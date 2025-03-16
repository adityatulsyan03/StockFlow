package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.DayBookReportApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DayBookReportRepo @Inject constructor(
    private val dayBookReportApi: DayBookReportApi
) {

    suspend fun getDayBookReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = dayBookReportApi.getAllDayBookReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Day book report retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve day book report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun postDayBookReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = dayBookReportApi.postDayBookReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Day book report added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add day book report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateDayBookReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = dayBookReportApi.updateDayBookReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Day book report updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update day book report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteDayBookReport(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = dayBookReportApi.deleteDayBookReport()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Day book report deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete day book report"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}