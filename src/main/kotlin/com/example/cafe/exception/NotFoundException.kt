package com.example.cafe.exception

import com.example.board.exception.BaseException
import com.example.cafe.web.response.ApiResponseCode

class NotFoundException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.NOT_FOUND
    override var message: String = message
}