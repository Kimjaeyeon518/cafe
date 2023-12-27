package com.example.cafe.exception

import com.example.board.exception.BaseException
import com.example.cafe.web.response.ApiResponseCode
import com.example.board.web.response.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
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

    @ExceptionHandler(SignatureException::class)
    fun handleSignatureException() =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(ApiResponseCode.UNAUTHORIZED, "토큰이 유효하지 않습니다."))

    @ExceptionHandler(MalformedJwtException::class)
    fun handleMalformedJwtException() =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(ApiResponseCode.UNAUTHORIZED, "올바르지 않은 토큰입니다."))

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwtException() =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(ApiResponseCode.UNAUTHORIZED, "토큰이 만료되었습니다. 다시 로그인해주세요."))

}
