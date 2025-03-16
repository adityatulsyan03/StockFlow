package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApi {

    @GET("")
    suspend fun getUser(@Query("name") name: String): CustomResponse<String>

    @PUT("")
    fun updateUser(@Query("name") name: String): CustomResponse<String>

    @DELETE("")
    fun deleteUser(@Query("name") name: String): CustomResponse<String>

    @POST("")
    fun addUser(): CustomResponse<String>

}