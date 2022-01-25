package com.shopback.model

import io.micronaut.data.annotation.*
import io.micronaut.data.model.naming.NamingStrategies
import java.time.LocalDateTime

@MappedEntity(
    value = "users",
    namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase::class
)
data class User(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.IDENTITY)
    val userId: Int? = null,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String?,
    @field:DateCreated
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @field:DateUpdated
    val updatedAt: LocalDateTime? = null
)

data class GetUser(
    val userId: Int,
    val email: String,
    val firstName: String,
    val lastName: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)

data class CreateUser(
    val firstName: String,
    val lastName: String?,
    val email: String,
    val password: String
)

data class UpdateUser(
    val firstName: String? = null,
    val lastName: String? = null
)

fun User.toGetUser(): GetUser {
    return GetUser(
        userId = this.userId!!,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
