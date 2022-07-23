package com.example.myapplication.di

import com.example.myapplication.data.api.ApiTestInterface
import com.example.myapplication.domain.usecase.home.LoadUsersUseCase
import com.example.myapplication.domain.usecase.home.LoadUsersUseCaseImpl
import com.example.myapplication.data.repositories.UserRepository
import com.example.myapplication.data.repositories.UserRepositoryImpl
import com.example.myapplication.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    factory<UserRepository> {
        UserRepositoryImpl(
            remoteDataSource = get<ApiTestInterface>()
        )
    }

    factory<LoadUsersUseCase> {
        LoadUsersUseCaseImpl(
            userRepository = get<UserRepository>()
        )
    }

    viewModel {
        HomeViewModel(
            loadUsersUseCase = get<LoadUsersUseCase>()
        )
    }
}