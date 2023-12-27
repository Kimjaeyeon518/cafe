package com.example.cafe.api

import com.example.cafe.web.request.LoginRequest
import com.example.board.web.response.SingleResponse
import com.example.cafe.domain.entity.User
import com.example.cafe.service.UserService
import com.example.cafe.web.request.ChargeRequest
import com.example.cafe.web.request.SignUpRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserApi(
    private val userService: UserService
) {
    @PostMapping("/login")
    fun signIn(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<SingleResponse<String>> {
        val token = userService.login(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("로그인 성공", token))
    }

    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody request: SignUpRequest
    ): ResponseEntity<SingleResponse<String>> {
        userService.signUp(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.success("회원가입 성공"))
    }

    @PostMapping("/charge")
    fun charge(
        @AuthenticationPrincipal user: org.springframework.security.core.userdetails.User,
        @Valid @RequestBody request: ChargeRequest
    ): ResponseEntity<SingleResponse<User>> {
        val chargedUser = userService.charge(user.username.toLong(), request.chargePoint)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("충전 성공", chargedUser))
    }
}