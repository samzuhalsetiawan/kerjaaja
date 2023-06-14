package com.animebiru.kerjaaja.data.repository

import android.util.Log
import com.animebiru.kerjaaja.data.sources.remote.ApiService
import com.animebiru.kerjaaja.domain.exceptions.ResponseNullBodyException
import com.animebiru.kerjaaja.domain.exceptions.ResponseUnsuccessfulException
import com.animebiru.kerjaaja.domain.models.ProjectCategory
import com.animebiru.kerjaaja.domain.repository.ProjectCategoryRepository
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toProjectCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProjectCategoryRepositoryImpl @Inject constructor(
    private val mainApi: ApiService
): ProjectCategoryRepository {

    private val projectCategoryFlow: MutableStateFlow<List<ProjectCategory>> = MutableStateFlow(emptyList())

    override fun getProjectCategoriesStateFlow(): StateFlow<List<ProjectCategory>> {
        return projectCategoryFlow.asStateFlow()
    }

    override suspend fun getProjectCategories(
        page: Int,
        size: Int
    ): RepositoryResult<List<ProjectCategory>> {
        return try {
            val response = mainApi.getAllProjectCategories(page, size)
            return if (response.isSuccessful) {
                val body = response.body() ?: throw ResponseNullBodyException("MainApi", "getAllProjectCategories")
                val projectCategories = body.data.attributes.records.map { it.toProjectCategory() }
                if (projectCategories.isNotEmpty()) projectCategoryFlow.value = projectCategories
                RepositoryResult.Success(projectCategories)
            } else {
                val body = response.errorBody()?.string() ?: throw ResponseNullBodyException("MainApi", "getAllProjectCategories")
                val errorMessage = JSONObject(body).getJSONObject("errors").getString("message")
                throw ResponseUnsuccessfulException("MainApi", "getAllProjectCategories", errorMessage)
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