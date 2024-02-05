package com.example.cafe.service

import com.example.cafe.exception.BadRequestException
import com.example.board.exception.DuplicateException
import com.example.cafe.exception.NotFoundException
import com.example.cafe.domain.entity.User
import com.example.cafe.domain.enums.MemberRole
import com.example.cafe.exception.InvalidCredentialException
import com.example.cafe.plugin.JwtPlugin
import com.example.cafe.repository.UserRepository
import com.example.cafe.web.request.LoginRequest
import com.example.cafe.web.request.SignUpRequest
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {
    fun login(request: LoginRequest): String {
        val user = userRepository.findByUsername(request.username)
            ?: throw NotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.")

        if (!passwordEncoder.matches(request.password, user.password) ) {
            throw InvalidCredentialException("아이디 또는 비밀번호가 일치하지 않습니다.")
        }

        return jwtPlugin.generateToken("${user.userId}:${user.role}")
    }

    fun signUp(request: SignUpRequest): User {
        val result = userRepository.findByUsername(request.username)
        if (result != null) {
            throw DuplicateException("중복된 ID 입니다.")
        }
        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            role = when (request.role) {
                "USER" -> MemberRole.CUSTOMER
                "ADMIN" -> MemberRole.ADMIN
                else -> throw BadRequestException("Role이 유효하지 않습니다.")
            },
            point = request.point
        )
        return userRepository.save(user)
    }

    @Transactional
    fun charge(userId: Long, point: Long): User {
        val user = userRepository.findById(userId).orElseThrow()
        user.chargePoint(point)
        return user
    }
}