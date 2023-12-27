package com.example.board.exception

import com.example.board.web.response.ApiResponseCode

class NotFoundException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.NOT_FOUND
    override var message: String = message
}