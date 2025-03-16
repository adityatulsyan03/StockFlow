package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.*

interface TransactionReportApi {

    @GET("")
    suspend fun getAllTransactionReports(): CustomResponse<String>

    @POST("")
    suspend fun postTransactionReport(): CustomResponse<String>

    @PUT("")
    suspend fun updateTransactionReport(): CustomResponse<String>

    @DELETE("")
    suspend fun deleteTransactionReport(): CustomResponse<String>
}