package com.animebiru.kerjaaja.domain.exceptions

class ResponseNullBodyException(
    apiName: String,
    endpointMethodName: String
): ResponseException(apiName, endpointMethodName, "Response Body is Null")