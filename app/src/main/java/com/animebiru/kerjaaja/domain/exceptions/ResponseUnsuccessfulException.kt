package com.animebiru.kerjaaja.domain.exceptions

import retrofit2.Response

class ResponseUnsuccessfulException(
    apiName: String,
    endpointMethodName: String,
    val errorMessage: String
): ResponseException(apiName, endpointMethodName, "Response Unsuccessful: $errorMessage")