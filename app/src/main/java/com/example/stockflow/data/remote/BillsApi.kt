package com.example.stockflow.data.remote

import com.example.stockflow.data.model.Bills
import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface BillsApi {

    @GET("/bills")
    suspend fun getAllBills(
        @Header("Authorization") token: String
    ): CustomResponse<List<Bills>>

    @POST("/bills")
    suspend fun postBill(
        @Header("Authorization") token: String,
        @Body bill: Bills
    ): CustomResponse<Bills>

    @GET("/bills/{billId}")
    suspend fun getBill(
        @Header("Authorization") token: String,
        @Path("billId") billId: String
    ): CustomResponse<Bills>

    @DELETE("/bills/{billId}")
    suspend fun deleteBill(
        @Header("Authorization") token: String,
        @Path("billId") billId: String
    ): CustomResponse<Unit>

    @GET("/bills/party/{partyId}")
    suspend fun getBillsByParty(
        @Header("Authorization") token: String,
        @Path("partyId") partyId: String
    ): CustomResponse<List<Bills>>

    @GET("/bills/date-range")
    suspend fun getBillsByDateRange(
        @Header("Authorization") token: String,
        @Query("startDate") startDate: LocalDate,
        @Query("endDate") endDate: LocalDate
    ): CustomResponse<List<Bills>>

}
