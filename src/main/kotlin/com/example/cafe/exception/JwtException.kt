package com.example.cafe.exception

import com.example.board.exception.BaseException
import com.example.board.web.response.ApiResponseCode

class JwtException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.UNAUTHORIZED
    override var message: String = message
}