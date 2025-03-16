package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.*

interface StockSummaryApi {

    @GET("")
    suspend fun getAllStockSummaries(): CustomResponse<String>

    @POST("")
    suspend fun postStockSummary(): CustomResponse<String>

    @PUT("")
    suspend fun updateStockSummary(): CustomResponse<String>

    @DELETE("")
    suspend fun deleteStockSummary(): CustomResponse<String>
}