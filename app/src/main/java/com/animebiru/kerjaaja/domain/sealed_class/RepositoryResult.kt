package com.animebiru.kerjaaja.domain.sealed_class

import java.lang.Exception

sealed class RepositoryResult<T> {
    class Success<T>(val data: T): RepositoryResult<T>()
    class Error<T>(val exception: Exception): RepositoryResult<T>()
}
