package com.shopback.service.http

import com.shopback.model.RandomUsers
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.micronaut.reactor.http.client.ReactorHttpClient
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import java.net.URI


@Singleton
class RandomUsersClient(@Client("randomUsers")
                        private val httpClient: ReactorHttpClient) {
    private val uri: URI = UriBuilder
        .of("/api")
        .build()

    suspend fun getRandomUsers(): RandomUsers {
        val req = HttpRequest.GET<RandomUsers>(uri)
        return httpClient.toBlocking().retrieve(req, RandomUsers::class.java)
    }
}