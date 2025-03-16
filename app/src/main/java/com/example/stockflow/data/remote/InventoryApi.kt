package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InventoryApi {

    @GET("")
    suspend fun getAllItems(): CustomResponse<String>

    @GET("")
    suspend fun getItem(@Path("id") id: String): CustomResponse<String>

    @POST("")
    fun addItem(): CustomResponse<String>

    @PATCH("")
    fun updateItem(@Path("id") id: String): CustomResponse<String>

    @DELETE
    fun deleteItem(@Path("id") id: String): CustomResponse<String>

    @GET("")
    suspend fun getItemsByCategory(@Path("id") id: String): CustomResponse<String>

}