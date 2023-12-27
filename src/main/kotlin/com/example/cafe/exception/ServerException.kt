package com.example.cafe.exception

import com.example.board.exception.BaseException
import com.example.cafe.web.response.ApiResponseCode

class ServerException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.INTERNAL_SERVER_ERROR
    override var message: String = message
}