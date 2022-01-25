package com.shopback.service

import com.shopback.model.CreateUserPost
import com.shopback.model.UpdateUserPost
import com.shopback.model.UserPost
import com.shopback.repository.PostRepository
import io.micronaut.data.model.Pageable
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.time.LocalDateTime

interface PostService {
    suspend fun getPost(userId: Int, postId: Int): UserPost?
    suspend fun deletePost(userId: Int, postId: Int): Boolean
    suspend fun createPost(userId: Int, createPost: CreateUserPost): UserPost
    suspend fun updatePost(userId: Int, postId: Int, updatePost: UpdateUserPost): UserPost?
    suspend fun findPosts(userId: Int, page: Int = 1, rows: Int = 10): List<UserPost>
}


@Singleton
class PostServiceDBImpl(
    @Inject val repository: PostRepository
) : PostService {

    override suspend fun getPost(userId: Int, postId: Int): UserPost? {
        return repository.find(userId, postId)
    }

    override suspend fun deletePost(userId: Int, postId: Int): Boolean {
        return repository.delete(userId, postId) > 0
    }

    override suspend fun createPost(userId: Int, createPost: CreateUserPost): UserPost {
        val post = UserPost(
            userId = userId,
            title = createPost.title,
            content = createPost.content,
            createdAt = LocalDateTime.now()
        )
        return repository.save(post)
    }

    override suspend fun updatePost(userId: Int, postId: Int, updatePost: UpdateUserPost): UserPost? {
        val post = repository.find(userId, postId)
        return post?.let {
            val updated = it.copy(
                title = updatePost.title ?: it.title,
                content = updatePost.content ?: it.content
            )
            repository.update(updated)
        }
    }

    override suspend fun findPosts(userId: Int, page: Int, rows: Int): List<UserPost> {
        return repository.findByUserIdOrderByPostIdDesc(userId, Pageable.from(page, rows))
    }


}