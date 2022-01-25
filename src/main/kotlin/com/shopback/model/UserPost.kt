package com.shopback.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.*
import io.micronaut.data.model.naming.NamingStrategies
import java.time.LocalDateTime

@MappedEntity(
    value = "user_posts",
    namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase::class
)
data class UserPost(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.IDENTITY)
    var postId: Int? = null,
    val userId: Int,
    val title: String,
    val content: String,
    @field:DateCreated
    var createdAt: LocalDateTime,
    @field:DateUpdated
    var updatedAt: LocalDateTime? = null
)


data class GetUserPost(
    val postId: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)


@Introspected
data class CreateUserPost(
    val title: String,
    val content: String
)

@Introspected
data class UpdateUserPost(
    val title: String? = null,
    val content: String? = null
)

fun UserPost.toGetUserPost(): GetUserPost {
    return GetUserPost(
        postId = this.postId!!,
        userId = this.userId,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
