package com.animebiru.kerjaaja.domain.exceptions

import retrofit2.Response

/**
 * @param message pesan yang akan bakal tampil di log, bukan di ui/enduser
 */
abstract class ResponseException(
    apiName: String,
    endpointMethodName: String,
    message: String
): Exception("[$apiName:$endpointMethodName] $message")