package com.example.board.exception

import com.example.board.web.response.ApiResponseCode

abstract class BaseException: RuntimeException() {
    open lateinit var code: ApiResponseCode
    override lateinit var message: String
}