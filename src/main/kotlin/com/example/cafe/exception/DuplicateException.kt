package com.example.board.exception

import com.example.cafe.web.response.ApiResponseCode
import com.example.cafe.exception.BaseException

class DuplicateException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.DUPLICATE_ENTITY
    override var message: String = message
}