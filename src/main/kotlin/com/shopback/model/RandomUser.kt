package com.shopback.model

import io.micronaut.core.annotation.Introspected


@Introspected
data class RandomUsers(val results: List<RandomUser>)

data class RandomUser(
    val gender: String,
    val name: RandomUserName,
    val email: String,
    val phone: String
)

data class RandomUserName(val title: String, val first: String, val last: String)