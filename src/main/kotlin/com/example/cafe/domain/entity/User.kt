package com.example.cafe.domain.entity

import com.example.cafe.domain.enums.UserRole
import com.example.cafe.exception.BadRequestException
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(username: String, password: String, name: String, role: UserRole, point: Long = 0) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null   // 회원 PK

    @Column(name = "username", unique = true)
    var username: String = username   // 회원 ID

    @Column(name = "password")
    var password: String = password   // 회원 비밀번호

    @Column(name = "name")
    var name: String = name   // 회원 이름

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: UserRole = role   // 회원 이름

    @Column(name = "point")
    var point: Long = point // 회원 포인트

    fun chargePoint(point: Long) {
        this.point += point
    }

    fun deductPoint(point: Long) {
        if (this.point < point) {
            throw BadRequestException("포인트가 부족합니다.")
        }
        this.point -= point
    }
}