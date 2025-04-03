package com.example.stockflow.data.remote

import com.example.stockflow.data.model.Category
import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryApi {

    @PUT("/category/{categoryId}")
    suspend fun updateCategoryById(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: String,
        @Body category: Category
    ): CustomResponse<Category>

    @DELETE("/category/{categoryId}")
    suspend fun deleteCategoryById(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: String
    ): CustomResponse<Unit>

    @GET("/category")
    suspend fun getAllCategoriesByType(
        @Header("Authorization") token: String,
        @Query("type") type: String
    ): CustomResponse<List<Category>>

    @GET("/category")
    suspend fun getAllCategories(
        @Header("Authorization") token: String
    ): CustomResponse<List<Category>>

    @POST("/category")
    suspend fun addCategory(
        @Header("Authorization") token: String,
        @Body category: List<Category>
    ): CustomResponse<List<Category>>

}