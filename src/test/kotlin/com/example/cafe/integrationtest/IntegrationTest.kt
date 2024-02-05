package com.example.cafe.integrationtest

import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.MySQLContainer


@ActiveProfiles("test")
@SpringBootTest
@Sql("classpath:/db/init_table.sql")
@Sql("classpath:/db/dml.sql")
abstract class IntegrationTest {

    companion object {
        @JvmStatic
        var container: MySQLContainer<*> = MySQLContainer("mysql:8");

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
        }
    }

}

