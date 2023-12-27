package com.example.board.exception

import com.example.board.web.response.ApiResponseCode
import com.example.board.web.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(BaseException::class)
    protected fun handleBaseException(e: BaseException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(e.code.status)
            .body(ErrorResponse(e.code, e.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleIllegalStateException(
        e: MethodArgumentNotValidException,
        bindingResult: BindingResult
    ): ResponseEntity<ErrorResponse> {
        val defaultMessage = bindingResult.fieldError?.defaultMessage
        return ResponseEntity(
            ErrorResponse(ApiResponseCode.BAD_REQUEST, defaultMessage),
            HttpStatus.BAD_REQUEST
        )
    }
}
