package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthApi {

    @GET("auth/verify")
    suspend fun verifyToken(
        @Header("Authorization") accessToken: String
    ): CustomResponse<String>

}