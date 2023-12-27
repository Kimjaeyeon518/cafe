package com.example.cafe.exception

import com.example.board.exception.BaseException
import com.example.cafe.web.response.ApiResponseCode

class InvalidCredentialException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.FORBIDDEN
    override var message: String = message
}