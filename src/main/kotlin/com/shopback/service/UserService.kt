package com.shopback.service

import com.shopback.model.CreateUser
import com.shopback.model.RandomUsers
import com.shopback.model.UpdateUser
import com.shopback.model.User
import com.shopback.repository.UserRepository
import com.shopback.service.http.RandomUsersClient
import com.shopback.utils.toNullable
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*

interface UserService {
    suspend fun createUser(createUser: CreateUser): Int
    suspend fun updateUser(userId: Int, updateUser: UpdateUser): Boolean
    suspend fun deleteUser(userId: Int): Boolean
    suspend fun findUserByEmail(email: String): User?
    suspend fun findUserById(userId: Int): User?
    suspend fun getRandomUser(): RandomUsers
}


@Singleton
class UserServiceDBImpl : UserService {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var randomUsersClient: RandomUsersClient

    override suspend fun createUser(createUser: CreateUser): Int {
        val user = User(
            email = createUser.email,
            password = createUser.password,
            firstName = createUser.firstName,
            lastName = createUser.lastName
        )
        val result = userRepository.save(user)
        return result.userId!!
    }

    override suspend fun updateUser(userId: Int, updateUser: UpdateUser): Boolean {
        val user = userRepository.findById(userId)
        user?.let {
            val updated = it.copy(
                firstName = updateUser.firstName ?: it.firstName,
                lastName = updateUser.lastName ?: it.lastName
            )
            userRepository.update(updated)
            return true
        }
        return false
    }

    override suspend fun deleteUser(userId: Int): Boolean {
        return userRepository.deleteById(userId) > 0
    }

    override suspend fun findUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override suspend fun findUserById(userId: Int): User? {
        return userRepository.findById(userId)
    }

    override suspend fun getRandomUser(): RandomUsers {
        return randomUsersClient.getRandomUsers()
    }

}
