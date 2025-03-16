package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.*

interface BillsApi {

    @GET("bills")
    suspend fun getAllBills(@Query("name") name: String): CustomResponse<String>

    @GET("bills/{id}")
    suspend fun getBill(@Query("name") name: String): CustomResponse<String>

    @PUT("bills/{id}")
    suspend fun updateBill(@Query("name") name: String): CustomResponse<String>

    @DELETE("bills/{id}")
    suspend fun deleteBill(@Query("name") name: String): CustomResponse<String>

    @POST("bills")
    suspend fun postBill(@Query("name") name: String): CustomResponse<String>
}
