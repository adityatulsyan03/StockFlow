package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.*

interface BankApi {

    @GET("")
    suspend fun getBank(@Query("name") name: String): CustomResponse<String>

    @PUT("")
    suspend fun updateBank(@Query("name") name: String): CustomResponse<String>

}