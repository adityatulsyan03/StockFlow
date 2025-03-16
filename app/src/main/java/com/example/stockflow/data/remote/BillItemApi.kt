package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.*

interface BillItemApi {

    @GET("")
    suspend fun getBillItem(): CustomResponse<String>

    @POST("")
    suspend fun postBillItem(): CustomResponse<String>

    @PUT("")
    suspend fun updateBillItem(): CustomResponse<String>

    @DELETE("")
    suspend fun deleteBillItem(): CustomResponse<String>
}