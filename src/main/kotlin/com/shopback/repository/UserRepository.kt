package com.shopback.repository

import com.shopback.model.User
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@JdbcRepository
interface UserRepository : CrudRepository<User, Int> {
    fun findByEmail(email: String): Optional<User>
}