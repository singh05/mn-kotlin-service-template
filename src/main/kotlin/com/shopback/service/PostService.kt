package com.shopback.service

import com.shopback.model.CreateUserPost
import com.shopback.model.UpdateUserPost
import com.shopback.model.UserPost
import com.shopback.repository.PostRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

interface PostService {
    suspend fun getPost(userId: Int, postId: Int): UserPost?
    suspend fun deletePost(userId: Int, postId: Int): Boolean
    suspend fun createPost(userId: Int, post: CreateUserPost): Int
    suspend fun updatePost(userId: Int, postId: Int, post: UpdateUserPost): UserPost?
}


@Singleton
class PostServiceDBImpl(
    @Inject val postService: PostRepository
) : PostService {

    override suspend fun getPost(userId: Int, postId: Int): UserPost? {
        TODO()
    }

    override suspend fun deletePost(userId: Int, postId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun createPost(userId: Int, post: CreateUserPost): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(userId: Int, postId: Int, post: UpdateUserPost): UserPost? {
        TODO("Not yet implemented")
    }


}