package com.example.cafe.exception

import com.example.board.exception.BaseException
import com.example.cafe.web.response.ApiResponseCode

class JwtException(
    override var code: ApiResponseCode = ApiResponseCode.UNAUTHORIZED,
    override var message: String
): BaseException()