package com.example.cafe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class CafeApplication

fun main(args: Array<String>) {
    runApplication<CafeApplication>(*args)
}
