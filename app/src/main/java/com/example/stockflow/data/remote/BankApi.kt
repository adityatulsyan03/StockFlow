package com.example.stockflow.data.remote

import com.example.stockflow.data.model.Bank
import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface BankApi {

    @GET("/bank")
    suspend fun getBank(
        @Header("Authorization") token: String
    ): CustomResponse<Bank>

    @PUT("/bank")
    suspend fun updateBank(
        @Header("Authorization") token: String,
        @Body bank: Bank
    ): CustomResponse<Bank>

}