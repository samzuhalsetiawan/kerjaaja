package com.animebiru.kerjaaja.domain.sealed_class

import java.lang.Exception

sealed class Result<T> {
    class Success<T>(val data: T): Result<T>()
    class Error<T>(val exception: Exception): Result<T>()
}
