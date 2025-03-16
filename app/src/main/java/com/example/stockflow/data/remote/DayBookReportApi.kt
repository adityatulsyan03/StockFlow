package com.example.stockflow.data.remote

import com.example.stockflow.data.model.CustomResponse
import retrofit2.http.*

interface DayBookReportApi {

    @GET("")
    suspend fun getAllDayBookReport(): CustomResponse<String>

    @POST("")
    suspend fun postDayBookReport(): CustomResponse<String>

    @PUT("")
    suspend fun updateDayBookReport(): CustomResponse<String>

    @DELETE("")
    suspend fun deleteDayBookReport(): CustomResponse<String>
}