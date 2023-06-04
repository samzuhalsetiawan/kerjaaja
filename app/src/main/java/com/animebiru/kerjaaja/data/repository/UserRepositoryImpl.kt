package com.animebiru.kerjaaja.data.repository

import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.data.sources.remote.ApiService
import com.animebiru.kerjaaja.domain.enums.Gender
import com.animebiru.kerjaaja.domain.enums.Role
import com.animebiru.kerjaaja.domain.exceptions.ResponseNullBodyException
import com.animebiru.kerjaaja.domain.exceptions.ResponseUnsuccessfulException
import com.animebiru.kerjaaja.domain.repository.UserRepository
import com.animebiru.kerjaaja.domain.sealed_class.Result
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor (
    private val dataStorePreferences: DataStorePreferences,
    private val mainApi: ApiService
) : UserRepository {

    override suspend fun login(username: String, password: String): Result<String> {
        return try {
            val response = mainApi.postLogin(username, password)
            return if (response.isSuccessful) {
                val body = response.body() ?: throw ResponseNullBodyException("MainApi", "postLogin")
                val accessToken = body.data.attributes.accessToken
                Result.Success(accessToken)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "postLogin")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "postLogin", errorMessage)
            }
        } catch (responseUnsuccessfulException: ResponseUnsuccessfulException) {
            responseUnsuccessfulException.printStackTrace()
            val errorMessage = responseUnsuccessfulException.errorMessage
            Result.Error(Exception(errorMessage, responseUnsuccessfulException))
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            Result.Error(Exception("Jaringan Error, Pastikan Anda memiliki koneksi internet", ioException))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            Result.Error(Exception("Server Error: ${httpException.code()}", httpException))
        } catch (tr: Throwable) {
            tr.printStackTrace()
            Result.Error(Exception("Unhandled Error, See Error Log", tr))
        }
    }

    override suspend fun register(
        username: String,
        fullName: String,
        gender: Gender,
        password: String
    ): Result<Unit> {
        return try {
            val response = mainApi.postRegister(username, fullName, Role.USER.label, password, gender.label)
            return if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "postRegister")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "postRegister", errorMessage)
            }
        } catch (responseUnsuccessfulException: ResponseUnsuccessfulException) {
            responseUnsuccessfulException.printStackTrace()
            val errorMessage = responseUnsuccessfulException.errorMessage
            Result.Error(Exception(errorMessage, responseUnsuccessfulException))
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            Result.Error(Exception("Jaringan Error, Pastikan Anda memiliki koneksi internet", ioException))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            Result.Error(Exception("Server Error: ${httpException.code()}", httpException))
        } catch (tr: Throwable) {
            tr.printStackTrace()
            Result.Error(Exception("Unhandled Error, See Error Log", tr))
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            dataStorePreferences.deleteAccessToken()
            Result.Success(Unit)
        } catch (tr: Throwable) {
            tr.printStackTrace()
            Result.Error(Exception("Unhandled Error, See Error Log", tr))
        }
    }
}