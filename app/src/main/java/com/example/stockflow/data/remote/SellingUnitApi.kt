package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.SellingUnit
import retrofit2.http.*

interface SellingUnitApi {

    @GET("/unit")
    suspend fun getAllSellingUnits(
        @Header ("Authorization") token: String
    ): CustomResponse<List<SellingUnit>>

    @POST("/unit")
    suspend fun postSellingUnit(
        @Header ("Authorization") token: String,
        @Body sellingUnit: List<SellingUnit>
    ): CustomResponse<List<SellingUnit>>
 
    @DELETE("/unit/{unitId}")
    suspend fun deleteSellingUnit(
        @Header ("Authorization") token: String,
        @Path("unitId") unitId: String
    ): CustomResponse<Unit>
}