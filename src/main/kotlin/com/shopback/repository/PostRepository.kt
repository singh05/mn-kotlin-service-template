package com.shopback.repository

import com.shopback.model.User
import com.shopback.model.UserPost
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@JdbcRepository
interface PostRepository : CrudRepository<UserPost, Int> {
    fun findByUserId(userId: Int): List<UserPost>
}