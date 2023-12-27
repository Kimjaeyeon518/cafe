package com.example.board.exception

import com.example.board.web.response.ApiResponseCode

class DuplicateException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.DUPLICATE_ENTITY
    override var message: String = message
}