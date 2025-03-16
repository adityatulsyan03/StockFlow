package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.SellingUnitApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SellingUnitRepo @Inject constructor(
    private val sellingUnitApi: SellingUnitApi
) {

    suspend fun getAllSellingUnits(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = sellingUnitApi.getAllSellingUnits()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Selling units retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve selling units"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun postSellingUnit(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = sellingUnitApi.postSellingUnit()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Selling unit added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add selling unit"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateSellingUnit(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = sellingUnitApi.updateSellingUnit()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Selling unit updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update selling unit"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteSellingUnit(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = sellingUnitApi.deleteSellingUnit()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Selling unit deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete selling unit"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}