package com.example.myapplication.data.api

import com.example.myapplication.data.entities.User
import retrofit2.http.GET

interface ApiTestInterface {
    @GET("/users")
    suspend fun getUsers() : User
}