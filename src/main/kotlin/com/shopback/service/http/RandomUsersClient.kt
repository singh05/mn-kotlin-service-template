package com.shopback.service.http

import com.shopback.model.RandomUsers
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import jakarta.inject.Singleton
import java.net.URI


@Singleton
class RandomUsersClient(@Client("randomUsers")
                        private val httpClient: HttpClient) {
    private val uri: URI = UriBuilder
        .of("/api")
        .build()

    fun getRandomUsers(): RandomUsers {
        val req = HttpRequest.GET<RandomUsers>(uri)
        return httpClient.toBlocking().retrieve(req, RandomUsers::class.java)
    }
}