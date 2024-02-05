package com.example.cafe.exception

import com.example.cafe.web.response.ApiResponseCode

abstract class BaseException: RuntimeException() {
    open lateinit var code: ApiResponseCode
    override lateinit var message: String
}