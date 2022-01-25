package com.shopback.controller

import com.shopback.model.*
import com.shopback.service.UserService
import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.validation.Valid
import io.micronaut.http.HttpResponse.*

@Controller
open class UserController {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Inject
    lateinit var userService: UserService

    @Get(uri = "/users/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    open suspend fun getUser(userId: Int): HttpResponse<Any> {
        logger.info("Inside getUser: $userId")
        val user = userService.findUserById(userId)?.toGetUser()
        return user?.let {
            logger.info("Returning getUser: $it")
            ok(it)
        } ?: run {
            logger.info("User not found")
            notFound()
        }
    }

    @Post(uri = "/users")
    @Produces(MediaType.APPLICATION_JSON)
    open suspend fun createUser(@Body @Valid createUser: CreateUser): HttpResponse<Any> {
        logger.info("Inside createUser: $createUser")
        val userId = userService.createUser(createUser)
        return getUser(userId)
    }

    @Put(uri = "/users/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    open suspend fun updateUser(userId: Int, @Body @Valid updateUser: UpdateUser): HttpResponse<Any> {
        logger.info("Inside updateUser: $updateUser")
        userService.updateUser(userId, updateUser)
        return getUser(userId)
    }

    @Delete(uri = "/users/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    open suspend fun deleteUser(userId: Int): HttpResponse<Any> {
        logger.info("Inside deleteUser: $userId")
        userService.deleteUser(userId)
        return HttpResponse.status(HttpStatus.OK)
    }


    @Get(uri = "/users/random")
    @Produces(MediaType.APPLICATION_JSON)
    open suspend fun getRandomUser(): HttpResponse<Any> {
        logger.info("Inside random user")
        val user = userService.getRandomUser()
        return HttpResponse.status<RandomUsers>(HttpStatus.OK).body(user)
    }

    @Get(uri = "/users")
    @Produces(MediaType.APPLICATION_JSON)
    open suspend fun findByEmail(@Parameter email: String): HttpResponse<Any> {
        logger.info("Inside Email")
        val user = userService.findUserByEmail(email)
        user?.let {
            return HttpResponse.status<User>(HttpStatus.OK).body(it)
        } ?: run {
            return HttpResponse.status(HttpStatus.NOT_FOUND)
        }
    }
}