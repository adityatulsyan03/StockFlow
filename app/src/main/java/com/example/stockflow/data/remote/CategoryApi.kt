package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface CategoryApi {

    @GET("")
    suspend fun getAllCategories(): CustomResponse<String>

    @POST("")
    fun addCategory(@Query("name") name: String): CustomResponse<String>

    @PUT("")
    fun updateCategory(@Query("name") name: String): CustomResponse<String>

    @DELETE("")
    fun deleteCategory(@Query("name") name: String): CustomResponse<String>

    @GET("")
    suspend fun getCategory(@Query("name") name: String): CustomResponse<String>

}