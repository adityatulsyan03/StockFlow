package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.*

interface MoneyReportApi {

    @GET("")
    suspend fun getAllMoneyReport(): CustomResponse<String>

    @POST("")
    suspend fun postMoneyReport(): CustomResponse<String>

    @PUT("")
    suspend fun updateMoneyReport(): CustomResponse<String>

    @DELETE("")
    suspend fun deleteMoneyReport(): CustomResponse<String>
}