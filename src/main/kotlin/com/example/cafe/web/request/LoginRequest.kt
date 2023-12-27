package com.example.board.web.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignInRequest(
    @field:NotBlank
    @field:Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.")
    var username: String,

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{4,10}", message = "비밀번호는 최소 4자 이상, 10자 이하이며 알파벳 대소문자(a~z, A~Z), 특수문자로 구성되어야 합니다.")
    var password: String
)