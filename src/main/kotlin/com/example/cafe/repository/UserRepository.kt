package com.example.cafe.repository

import com.example.cafe.domain.entity.User
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

//    @Query("SELECT 1 * FROM users WHERE username = :username LIMIT 1", nativeQuery = true)
//    fun findByUsername(username: String): User?

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.userId = :id")
    fun findByIdWithPessimisticLock(id: Long): User?
}