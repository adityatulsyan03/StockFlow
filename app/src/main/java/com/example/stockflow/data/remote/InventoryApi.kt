package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.Inventory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface InventoryApi {

    @GET("/inventory/{inventoryId}")
    suspend fun getInventoryById(
        @Header("Authorization") token: String,
        @Path("inventoryId") inventoryId: String
    ): CustomResponse<Inventory>

    @PUT("/inventory/{inventoryId}")
    suspend fun updateInventoryById(
        @Header("Authorization") token: String,
        @Path("inventoryId") inventoryId: String,
        @Body inventory: Inventory
    ): CustomResponse<Inventory>

    @DELETE("/inventory/{inventoryId}")
    suspend fun deleteInventoryById(
        @Header("Authorization") token: String,
        @Path("inventoryId") inventoryId: String
    ): CustomResponse<Unit>

    @GET("/inventory")
    suspend fun getAllInventories(
        @Header("Authorization") token: String
    ): CustomResponse<List<Inventory>>

    @POST("/inventory")
    suspend fun addInventory(
        @Header("Authorization") token: String,
        @Body inventory: Inventory
    ): CustomResponse<Inventory>
}