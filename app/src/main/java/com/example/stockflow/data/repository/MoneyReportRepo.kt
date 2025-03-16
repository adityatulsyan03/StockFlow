package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.MoneyReportApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoneyReportRepo @Inject constructor(
    private val moneyReportApi: MoneyReportApi
) {

    suspend fun getAllMoneyReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = moneyReportApi.getAllMoneyReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Money report retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve money report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun postMoneyReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = moneyReportApi.postMoneyReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Money report added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add money report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateMoneyReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = moneyReportApi.updateMoneyReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Money report updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update money report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteMoneyReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = moneyReportApi.deleteMoneyReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Money report deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete money report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}