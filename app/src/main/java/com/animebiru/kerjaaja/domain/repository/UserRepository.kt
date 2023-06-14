package com.animebiru.kerjaaja.domain.repository

import com.animebiru.kerjaaja.domain.enums.Gender
import com.animebiru.kerjaaja.domain.models.User
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import kotlinx.coroutines.flow.StateFlow
import java.io.File

interface UserRepository {

    suspend fun login(username: String, password: String): RepositoryResult<String>

    suspend fun register(username: String, fullName: String, gender: Gender, password: String): RepositoryResult<Unit>

    suspend fun logout(): RepositoryResult<Unit>

    suspend fun updateUserProfilePicture(image: File): RepositoryResult<Unit>

    suspend fun getDetailUser(): RepositoryResult<User>

    fun getDetailUserStateFlow(): StateFlow<User?>

}