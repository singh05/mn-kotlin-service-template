package com.shopback.repository

import com.shopback.model.User
import io.micronaut.context.annotation.Executable
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
interface UserRepository : CoroutineCrudRepository<User, Int> {

    @Executable
    suspend fun findByEmail(email: String): User?
}