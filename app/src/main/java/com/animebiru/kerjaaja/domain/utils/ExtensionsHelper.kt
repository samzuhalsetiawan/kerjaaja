package com.animebiru.kerjaaja.domain.utils

import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.animebiru.kerjaaja.data.sources.local.entity.HistoryProjectEntity
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectCategoryEntity
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity
import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.ProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.UserDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.project.category.CategoryDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.project.category.project_category.ProjectCategoryDto
import com.animebiru.kerjaaja.domain.application.KerjaAjaApp
import com.animebiru.kerjaaja.domain.enums.Gender
import com.animebiru.kerjaaja.domain.models.ProjectCategory
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.domain.models.User
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object ExtensionsHelper {

    private const val BASE_URL = KerjaAjaApp.BASE_URL

    fun <T> AppCompatActivity.collectFlowWhenStarted(flow: Flow<T>, collector: suspend (data: T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect(collector)
            }
        }
    }

    fun <T> AppCompatActivity.collectLatestFlowWhenStarted(flow: Flow<T>, collector: suspend (data: T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collector)
            }
        }
    }

    fun AppCompatActivity.repeatCoroutineWhenStarted(callback: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED, callback)
        }
    }

    fun <T> Fragment.collectFlowWhenStarted(flow: Flow<T>, collector: suspend (data: T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect(collector)
            }
        }
    }
    fun <T> Fragment.collectLatestFlowWhenStarted(flow: Flow<T>, collector: suspend (data: T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collector)
            }
        }
    }

    fun Fragment.repeatCoroutineWhenStarted(callback: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED, callback)
        }
    }

    fun String?.indexesOf(subStr: String, ignoreCase: Boolean = true): List<Int> {
        return this?.let {
            val regex = if (ignoreCase) Regex(subStr, RegexOption.IGNORE_CASE) else Regex(subStr)
            regex.findAll(this).map { it.range.first }.toList()
        } ?: emptyList()
    }

//    Converters

    private fun Number.toPx(): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)
    }

    val Number.dp: Float
        get() {
            return this.toFloat().toPx()
        }

    fun File.toImagePart(partName: String): MultipartBody.Part {
        val imageType = HelperUtil.getFileExtensionFromFile(this, true)
        val imageAsRequestBody = asRequestBody("image/${imageType}".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, this.name, imageAsRequestBody)
    }

    fun String.toGender(): Gender? {
        return Gender.values().find { it.label.lowercase() == this.lowercase() }
    }

    fun ProjectDto.toProject(): Project {
        val photoUrl = "$BASE_URL${owner.photoUrl}"
        val listOfCategoriesName = categories.map { it.projectCategory.name }
        val latLng = LatLng(latitude, longitude)
        val formattedFee = fee.substring(2).reversed().chunked(3).joinToString(".").reversed()
        return Project(id, owner.username, photoUrl, title, formattedFee, status, createdAt, listOfCategoriesName, latLng)
    }

    private fun CategoryDto.toProjectCategory(): ProjectCategory {
        val photoUrl = "$BASE_URL${projectCategory.photoUrl}"
        val timestamp = createdAt.toInstant().epochSeconds
        return ProjectCategory(projectCategory.name, photoUrl, timestamp)
    }

    fun ProjectCategoryDto.toProjectCategory(): ProjectCategory {
        val photoUrl = "$BASE_URL${this.photoUrl}"
        val timestamp = createdAt.toInstant().epochSeconds
        return ProjectCategory(name, photoUrl, timestamp)
    }

    fun ProjectCategory.toEntity(page: Int): ProjectCategoryEntity {
        return ProjectCategoryEntity(title, photoUrl, createdAt, page)
    }

    fun ProjectCategoryEntity.toProjectCategory(): ProjectCategory {
        return ProjectCategory(title, photoUrl, createdAt)
    }

    fun ProjectEntity.toProject(): Project {
        val latLng = LatLng(latitude.toDouble(), longitude.toDouble())
//        val formattedFee = fee.substring(2).reversed().chunked(3).joinToString(".").reversed()
        return Project(id, creator, photoUrl, shortJobDesc, fee, status, createdAt, emptyList(), latLng)
    }

    fun Project.toEntity(page: Int): ProjectEntity {
        return ProjectEntity(id, creator, photoUrl, shortJobDesc, status, fee, createdAt, latLng.latitude.toString(), latLng.longitude.toString(), page)
    }

    fun UserDto.toUser(): User {
        val photoUrl = "$BASE_URL${this.photoUrl}"
        return User(createdAt, fullname, gender, photoUrl, updatedAt, username)
    }

    fun User.toUserDto(): UserDto {
        return UserDto(createdAt, fullName, gender, photoUrl, updatedAt, username)
    }

    fun ProjectEntity.toProjectHistory(): HistoryProjectEntity {
        return HistoryProjectEntity(id, creator, photoUrl, shortJobDesc, status, fee, createdAt, latitude, longitude, page)
    }

    fun HistoryProjectEntity.toProjectEntity(): ProjectEntity {
        return ProjectEntity(id, creator, photoUrl, shortJobDesc, status, fee, createdAt, latitude, longitude, page)
    }

}