package com.animebiru.kerjaaja.domain.repository

import com.animebiru.kerjaaja.domain.enums.Gender
import com.animebiru.kerjaaja.domain.sealed_class.Result

interface UserRepository {

    suspend fun login(username: String, password: String): Result<String>

    suspend fun register(username: String, fullName: String, gender: Gender, password: String): Result<Unit>

    suspend fun logout(): Result<Unit>

}