package com.example.board.exception

import com.example.board.web.response.ApiResponseCode
import org.springframework.http.HttpStatus

class BadRequestException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.BAD_REQUEST
    override var message: String = message
}