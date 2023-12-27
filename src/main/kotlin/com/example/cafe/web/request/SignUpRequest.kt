package com.example.cafe.web.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignUpRequest(
    @field:NotBlank
    @field:Email(message = "이메일 형식이 아닙니다.")
    var username: String,

    @field:NotBlank
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{4,10}", message = "비밀번호는 최소 4자 이상, 10자 이하이며 알파벳 대소문자(a~z, A~Z), 특수문자로 구성되어야 합니다.")
    var password: String,

    @field:NotBlank
    var name: String,

    @field:NotBlank
    var role: String,

    var point: Long = 0
)