package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.DayBookReport
import com.example.stockflow.data.model.MoneyReport
import com.example.stockflow.data.model.StockReport
import com.example.stockflow.data.model.TransactionReport
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ReportApi {

    @GET("/reports/transactions")
    suspend fun getTransactionsReport(
        @Header("Authorization") token: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): CustomResponse<List<TransactionReport>>

    @GET("/reports/stock")
    suspend fun getStockReport(
        @Header("Authorization") token: String
    ): CustomResponse<List<StockReport>>

    @GET("/reports/stock/{inventoryId}")
    suspend fun getStockReportByInventoryId(
        @Header("Authorization") token: String,
        @Path("inventoryId") inventoryId: String
    ): CustomResponse<StockReport>

    @GET("/reports/money")
    suspend fun getMoneyReport(
        @Header("Authorization") token: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): CustomResponse<List<MoneyReport>>

    @GET("/reports/daybook")
    suspend fun getDaybookReport(
        @Header("Authorization") token: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): CustomResponse<List<DayBookReport>>

    @GET("/reports/daybook/{date}")
    suspend fun getDaybookReportByDate(
        @Header("Authorization") token: String,
        @Path("date") date: String
    ): CustomResponse<DayBookReport>
}