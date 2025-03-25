package com.example.stockflow.data.repository

import android.util.Log
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.*
import com.example.stockflow.data.remote.ReportApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReportRepo @Inject constructor(
    private val reportApi: ReportApi
) {

    suspend fun getTransactionsReport(token: String, startDate: String, endDate: String): Flow<UiState<CustomResponse<List<TransactionReport>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = reportApi.getTransactionsReport(token, startDate, endDate)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Transactions report fetched successfully"))
                Log.d("ReportRepo", response.data.toString())
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to fetch transactions report"))
                Log.d("ReportRepo", response.message.toString())
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
            Log.d("ReportRepo", e.message.toString())
        }
    }

    suspend fun getStockReport(token: String): Flow<UiState<CustomResponse<List<StockReport>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = reportApi.getStockReport(token)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Stock report fetched successfully"))
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to fetch stock report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getStockReportByInventoryId(token: String, inventoryId: String): Flow<UiState<CustomResponse<StockReport>>> = flow {
        try {
            emit(UiState.Loading)
            val response = reportApi.getStockReportByInventoryId(token, inventoryId)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Stock report for inventory ID fetched successfully"))
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to fetch stock report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getMoneyReport(token: String, startDate: String, endDate: String): Flow<UiState<CustomResponse<List<MoneyReport>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = reportApi.getMoneyReport(token, startDate, endDate)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Money report fetched successfully"))
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to fetch money report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getDaybookReport(token: String, startDate: String, endDate: String): Flow<UiState<CustomResponse<List<DayBookReport>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = reportApi.getDaybookReport(token, startDate, endDate)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Daybook report fetched successfully"))
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to fetch daybook report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getDaybookReportByDate(token: String, date: String): Flow<UiState<CustomResponse<DayBookReport>>> = flow {
        try {
            emit(UiState.Loading)
            val response = reportApi.getDaybookReportByDate(token, date)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Daybook report for date fetched successfully"))
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to fetch daybook report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}