package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.Party
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PartyApi {

    @GET("/party/{partyId}")
    suspend fun getPartyById(
        @Header("Authorization") token: String,
        @Path("partyId") partyId: String
    ): CustomResponse<Party>

    @PUT("/party/{partyId}")
    suspend fun updatePartyById(
        @Header("Authorization") token: String,
        @Path("partyId") partyId: String,
        @Body party: Party
    ): CustomResponse<Party>

    @DELETE("/party/{partyId}")
    suspend fun deletePartyById(
        @Header("Authorization") token: String,
        @Path("partyId") partyId: String
    ): CustomResponse<Unit>

    @GET("/party")
    suspend fun getAllParties(
        @Header("Authorization") token: String
    ): CustomResponse<List<Party>>

    @POST("/party")
    suspend fun createParty(
        @Header("Authorization") token: String,
        @Body party: Party
    ): CustomResponse<Party>
}