package com.example.board.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(username: String, password: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null   // 회원 PK

    @Column(name = "username", unique = true)
    var username: String = username   // 회원 이름

    @Column(name = "password")
    var password: String = password   // 회원 비밀번호

}