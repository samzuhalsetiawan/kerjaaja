package com.animebiru.kerjaaja.data.repository

import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.data.sources.remote.ApiService
import com.animebiru.kerjaaja.domain.enums.ProjectStatus
import com.animebiru.kerjaaja.domain.exceptions.ResponseNullBodyException
import com.animebiru.kerjaaja.domain.exceptions.ResponseUnsuccessfulException
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.domain.repository.ProjectRepository
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toProject
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val mainApi: ApiService,
    private val dataStorePreferences: DataStorePreferences
): ProjectRepository {

    override suspend fun getProjects(page: Int, size: Int): RepositoryResult<List<Project>> {
        return try {
            val response = mainApi.getAllProjects(page, size)
            return if (response.isSuccessful) {
                val body = response.body() ?: throw ResponseNullBodyException("MainApi", "getAllProjects")
                val projects = body.data.attributes.records.map { it.toProject() }
                RepositoryResult.Success(projects)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "getAllProjects")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "getAllProjects", errorMessage)
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

    override suspend fun createProject(
        title: String,
        status: ProjectStatus,
        fee: Double,
        deadline: String,
        latitude: Float,
        longitude: Float,
        categories: List<String>
    ): RepositoryResult<Unit> {
        return try {
            val username = dataStorePreferences.getUsernameFlow().first() ?: throw NullPointerException("username empty")
            val jsonCategories = JSONArray(categories)
            val response = mainApi.createProject(title, status.label, fee, deadline, username, latitude, longitude, jsonCategories)
            return if (response.isSuccessful) {
                RepositoryResult.Success(Unit)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "createProject")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "createProject", errorMessage)
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

    override suspend fun getProjectById(
        projectId: String,
        page: Int,
        size: Int
    ): RepositoryResult<Project> {
        return try {
            val response = mainApi.getProjectById(page, size, projectId)
            return if (response.isSuccessful) {
                val body = response.body() ?: throw ResponseNullBodyException("MainApi", "getProjectById")
                val projects = body.data.attributes.toProject()
                RepositoryResult.Success(projects)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "getProjectById")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "getProjectById", errorMessage)
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

    override suspend fun getUserCreatedProjects(
        page: Int,
        size: Int
    ): RepositoryResult<List<Project>> {
        return RepositoryResult.Success(emptyList())
    }

    override suspend fun getProjectsByOwner(
        owner: String,
        page: Int,
        size: Int
    ): RepositoryResult<List<Project>> {
        return try {
            val response = mainApi.getProjectsByOwner(owner, page, size)
            return if (response.isSuccessful) {
                val body = response.body() ?: throw ResponseNullBodyException("MainApi", "getProjectsByOwner")
                val projects = body.data.attributes.records.map { it.toProject() }
                RepositoryResult.Success(projects)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "getProjectsByOwner")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "getProjectsByOwner", errorMessage)
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