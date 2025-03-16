package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.BillsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BillsRepo @Inject constructor(
    private val billsApi: BillsApi
) {

    suspend fun getAllBills(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.getAllBills(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bills retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve bills"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getBill(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.getBill(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bill retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve bill"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateBill(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.updateBill(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bill updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update bill"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteBill(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.deleteBill(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bill deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete bill"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun postBill(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.postBill(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bill added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add bill"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}