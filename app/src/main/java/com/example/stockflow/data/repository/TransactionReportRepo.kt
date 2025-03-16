package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.TransactionReportApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TransactionReportRepo @Inject constructor(
    private val transactionReportApi: TransactionReportApi
) {

    suspend fun getAllTransactionReports(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = transactionReportApi.getAllTransactionReports()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Transaction reports retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve transaction reports"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun postTransactionReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = transactionReportApi.postTransactionReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Transaction report added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add transaction report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateTransactionReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = transactionReportApi.updateTransactionReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Transaction report updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update transaction report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteTransactionReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = transactionReportApi.deleteTransactionReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Transaction report deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete transaction report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}