package com.example.myapplication.domain.usecase.home

import com.example.myapplication.data.entities.UseCaseResult
import com.example.myapplication.data.entities.User
import com.example.myapplication.data.repositories.UserRepository

interface LoadUsersUseCase {
    suspend fun execute(): UseCaseResult<User>
}

class LoadUsersUseCaseImpl(
    private val userRepository: UserRepository
) : LoadUsersUseCase {
    override suspend fun execute(): UseCaseResult<User> {
        return try {
            val result = userRepository.findUser()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

}