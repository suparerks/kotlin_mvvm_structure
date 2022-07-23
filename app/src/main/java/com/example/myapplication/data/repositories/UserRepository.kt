package com.example.myapplication.data.repositories

import com.example.myapplication.data.api.ApiTestInterface
import com.example.myapplication.data.entities.User

interface UserRepository {
    suspend fun findUser() : User
    suspend fun deleteUser(userId : String) : User
}

class UserRepositoryImpl (
    private val remoteDataSource : ApiTestInterface
) : UserRepository {
    override suspend fun findUser(): User {
        val result = remoteDataSource.getUsers()
        return result
    }

    override suspend fun deleteUser(userId: String): User {
        TODO("Not yet implemented")
    }
}