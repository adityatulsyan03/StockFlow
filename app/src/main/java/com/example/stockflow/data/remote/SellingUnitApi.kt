package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.*

interface SellingUnitApi {

    @GET("")
    suspend fun getAllSellingUnits(): CustomResponse<String>

    @POST("")
    suspend fun postSellingUnit(): CustomResponse<String>

    @PUT("")
    suspend fun updateSellingUnit(): CustomResponse<String>

    @DELETE("")
    suspend fun deleteSellingUnit(): CustomResponse<String>
}