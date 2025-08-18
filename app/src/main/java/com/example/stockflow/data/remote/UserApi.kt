package com.example.stockflow.data.remote

import com.example.stockflow.data.model.AccessTokenBody
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApi {

    @GET("/user")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): CustomResponse<User>

    @PUT("/user")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body user: User
    ): CustomResponse<User>

    @POST("/user")
    suspend fun authUser(
        @Body accessToken: AccessTokenBody
    ): CustomResponse<User>

    @DELETE("/user")
    suspend fun deleteUser(
        @Header("Authorization") token: String
    ): CustomResponse<Unit>

}