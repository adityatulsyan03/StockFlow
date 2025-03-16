package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.StockSummaryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StockSummaryRepo @Inject constructor(
    private val stockSummaryApi: StockSummaryApi
) {

    suspend fun getAllStockSummaries(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = stockSummaryApi.getAllStockSummaries()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Stock summaries retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve stock summaries"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun postStockSummary(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = stockSummaryApi.postStockSummary()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Stock summary added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add stock summary"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateStockSummary(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = stockSummaryApi.updateStockSummary()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Stock summary updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update stock summary"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteStockSummary(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = stockSummaryApi.deleteStockSummary()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Stock summary deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete stock summary"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}