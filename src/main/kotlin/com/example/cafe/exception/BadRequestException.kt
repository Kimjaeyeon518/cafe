package com.example.cafe.exception

import com.example.cafe.exception.BaseException
import com.example.cafe.web.response.ApiResponseCode

class BadRequestException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.BAD_REQUEST
    override var message: String = message
}