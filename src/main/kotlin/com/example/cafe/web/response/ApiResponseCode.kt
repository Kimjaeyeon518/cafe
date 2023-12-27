package com.example.board.web.response

import org.springframework.http.HttpStatus

enum class ApiResponseCode(status: HttpStatus, message: String) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    DUPLICATE_ENTITY(HttpStatus.BAD_REQUEST, "이미 해당 값이 존재합니다. 다시 입력해주세요."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 값을 찾을 수 없습니다."),
    ;

    val status: HttpStatus = status
    val message: String = message
}
