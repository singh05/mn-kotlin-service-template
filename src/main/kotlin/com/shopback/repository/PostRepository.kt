package com.shopback.repository

import com.shopback.model.UserPost
import io.micronaut.context.annotation.Executable
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.Sort
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface PostRepository : CoroutineCrudRepository<UserPost, Int> {


    @Executable
    fun findByUserIdOrderByPostIdDesc(userId: Int, pageable: Pageable): List<UserPost>

    @Executable
    suspend fun find(userId: Int, postId: Int): UserPost?

    @Executable
    suspend fun findByUserIdAndPostId(userId: Int, postId: Int): UserPost?

    @Executable
    suspend fun delete(userId: Int, postId: Int): Int

    @Executable
    suspend fun deleteByUserIdAndPostId(userId: Int, postId: Int): Int
}