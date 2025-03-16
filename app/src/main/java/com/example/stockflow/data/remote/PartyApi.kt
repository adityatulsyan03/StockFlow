package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface PartyApi {

    @GET("")
    suspend fun getAllParties(): CustomResponse<String>

    @POST("")
    fun addParty(): CustomResponse<String>

    @PATCH("")
    fun updateParty(): CustomResponse<String>

    @GET("")
    fun getParty(@Query("name") name: String): CustomResponse<String>

    @DELETE("")
    fun deleteParty(@Query("name") name:String): CustomResponse<String>

}