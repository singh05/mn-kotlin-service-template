package com.shopback.controller

import com.shopback.model.*
import com.shopback.service.PostService
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

@Controller("/users/{userId}/posts")
open class PostController {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Inject
    lateinit var postService: PostService

    @Get(uri = "/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getPost(userId: Int, postId: Int): HttpResponse<Any> {
        logger.info("Inside getPost: $postId")
        val post = postService.getPost(userId, postId)?.toGetUserPost()
        return post?.let {
            logger.info("Returning getPost: $it")
            ok(it)
        } ?: run {
            logger.info("Post not found")
            notFound()
        }
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findUserPosts(userId: Int): HttpResponse<Any> {
        logger.info("Inside findUserPosts: $userId")
        val users = postService.findPosts(userId, 0, 10)
        return ok(users)
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    open suspend fun createPost(userId: Int, @Body @Valid createPost: CreateUserPost): HttpResponse<Any> {
        logger.info("Inside createPost: $createPost")
        val post = postService.createPost(userId, createPost)
        return ok(post)
    }

    @Put(uri = "/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    open suspend fun updatePost(userId: Int, postId: Int, @Body @Valid updatePost: UpdateUserPost): HttpResponse<Any> {
        logger.info("Inside updatePost: $updatePost")
        val updatedPost = postService.updatePost(userId, postId, updatePost)
        updatedPost?.let {
            return ok(it)
        } ?: return notFound()
    }

    @Delete(uri = "/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deletePost(userId: Int, postId: Int): HttpResponse<Any> {
        logger.info("Inside deletePost for userId: $userId, postId: $postId")
        return if (postService.deletePost(userId, postId)) {
            accepted()
        } else {
            badRequest()
        }

    }
}