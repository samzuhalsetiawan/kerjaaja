package com.animebiru.kerjaaja.data.repository

import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.data.sources.remote.ApiService
import com.animebiru.kerjaaja.domain.enums.Gender
import com.animebiru.kerjaaja.domain.enums.Role
import com.animebiru.kerjaaja.domain.exceptions.ResponseNullBodyException
import com.animebiru.kerjaaja.domain.exceptions.ResponseUnsuccessfulException
import com.animebiru.kerjaaja.domain.models.User
import com.animebiru.kerjaaja.domain.repository.UserRepository
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toImagePart
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toUser
import com.animebiru.kerjaaja.domain.utils.HelperUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor (
    private val dataStorePreferences: DataStorePreferences,
    private val mainApi: ApiService
) : UserRepository {

    private val detailUser: MutableStateFlow<User?> = MutableStateFlow(null)

    override fun getDetailUserStateFlow(): StateFlow<User?> {
        return detailUser.asStateFlow()
    }

    override suspend fun login(username: String, password: String): RepositoryResult<String> {
        return try {
            val response = mainApi.postLogin(username, password)
            return if (response.isSuccessful) {
                val body = response.body() ?: throw ResponseNullBodyException("MainApi", "postLogin")
                val accessToken = body.data.attributes.accessToken
                dataStorePreferences.saveUsername(username)
                RepositoryResult.Success(accessToken)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "postLogin")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "postLogin", errorMessage)
            }
        } catch (responseUnsuccessfulException: ResponseUnsuccessfulException) {
            responseUnsuccessfulException.printStackTrace()
            val errorMessage = responseUnsuccessfulException.errorMessage
            RepositoryResult.Error(Exception(errorMessage, responseUnsuccessfulException))
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            RepositoryResult.Error(Exception("Jaringan Error, Pastikan Anda memiliki koneksi internet", ioException))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            RepositoryResult.Error(Exception("Server Error: ${httpException.code()}", httpException))
        } catch (tr: Throwable) {
            tr.printStackTrace()
            RepositoryResult.Error(Exception("Unhandled Error, See Error Log", tr))
        }
    }

    override suspend fun register(
        username: String,
        fullName: String,
        gender: Gender,
        password: String
    ): RepositoryResult<Unit> {
        return try {
            val response = mainApi.postRegister(username, fullName, Role.USER.label, password, gender.label)
            return if (response.isSuccessful) {
                RepositoryResult.Success(Unit)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "postRegister")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "postRegister", errorMessage)
            }
        } catch (responseUnsuccessfulException: ResponseUnsuccessfulException) {
            responseUnsuccessfulException.printStackTrace()
            val errorMessage = responseUnsuccessfulException.errorMessage
            RepositoryResult.Error(Exception(errorMessage, responseUnsuccessfulException))
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            RepositoryResult.Error(Exception("Jaringan Error, Pastikan Anda memiliki koneksi internet", ioException))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            RepositoryResult.Error(Exception("Server Error: ${httpException.code()}", httpException))
        } catch (tr: Throwable) {
            tr.printStackTrace()
            RepositoryResult.Error(Exception("Unhandled Error, See Error Log", tr))
        }
    }

    override suspend fun logout(): RepositoryResult<Unit> {
        return try {
            dataStorePreferences.deleteAccessToken()
            RepositoryResult.Success(Unit)
        } catch (tr: Throwable) {
            tr.printStackTrace()
            RepositoryResult.Error(Exception("Unhandled Error, See Error Log", tr))
        }
    }

    override suspend fun updateUserProfilePicture(image: File): RepositoryResult<Unit> {
        return try {
            val username = dataStorePreferences.getUsernameFlow().first() ?: throw NullPointerException("Username is null")
            val reducedImageFile = HelperUtil.reduceFileImage(image, 1_000_000)
            val photoPart = reducedImageFile.toImagePart("image")
            val response = mainApi.changePhoto(photoPart, username)
            return if (response.isSuccessful) {
                RepositoryResult.Success(Unit)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "changePhoto")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "changePhoto", errorMessage)
            }
        } catch (responseUnsuccessfulException: ResponseUnsuccessfulException) {
            responseUnsuccessfulException.printStackTrace()
            val errorMessage = responseUnsuccessfulException.errorMessage
            RepositoryResult.Error(Exception(errorMessage, responseUnsuccessfulException))
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            RepositoryResult.Error(Exception("Jaringan Error, Pastikan Anda memiliki koneksi internet", ioException))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            RepositoryResult.Error(Exception("Server Error: ${httpException.code()}", httpException))
        } catch (tr: Throwable) {
            tr.printStackTrace()
            RepositoryResult.Error(Exception("Unhandled Error, See Error Log", tr))
        }
    }

    override suspend fun getDetailUser(): RepositoryResult<User> {
        return try {
            val username = dataStorePreferences.getUsernameFlow().first() ?: throw NullPointerException("Username is null")
            val response = mainApi.getUserByUsername(username)
            return if (response.isSuccessful) {
                val body = response.body() ?: throw ResponseNullBodyException("MainApi", "getUserByUsername")
                val user = body.data.attributes.records.first { it.username == username }.toUser()
                detailUser.value = user
                RepositoryResult.Success(user)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "getUserByUsername")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "getUserByUsername", errorMessage)
            }
        } catch (responseUnsuccessfulException: ResponseUnsuccessfulException) {
            responseUnsuccessfulException.printStackTrace()
            val errorMessage = responseUnsuccessfulException.errorMessage
            RepositoryResult.Error(Exception(errorMessage, responseUnsuccessfulException))
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            RepositoryResult.Error(Exception("Jaringan Error, Pastikan Anda memiliki koneksi internet", ioException))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            RepositoryResult.Error(Exception("Server Error: ${httpException.code()}", httpException))
        } catch (tr: Throwable) {
            tr.printStackTrace()
            RepositoryResult.Error(Exception("Unhandled Error, See Error Log", tr))
        }
    }
}