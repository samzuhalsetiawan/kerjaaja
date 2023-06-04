package com.animebiru.kerjaaja.data.sources.remote

import android.util.Log
import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiConfig @Inject constructor(
    private val dataStorePreferences: DataStorePreferences
){

    private val authInterceptor by lazy {
        Interceptor { chain ->
            val accessToken = runBlocking { dataStorePreferences.getAccessTokenFlow().first() }
            val req = accessToken?.let { token ->
                chain.request().newBuilder()
                    .header("Authorization", token)
                    .build()
            } ?: chain.request()
            chain.proceed(req)
        }
    }

    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://kerjaaja-backend-production.up.railway.app/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}