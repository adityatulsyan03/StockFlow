package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.BillItemApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BillItemRepo @Inject constructor(
    private val billItemApi: BillItemApi
) {

    suspend fun getBillItem(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billItemApi.getBillItem()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bill item retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve bill item"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun postBillItem(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billItemApi.postBillItem()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bill item added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add bill item"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateBillItem(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billItemApi.updateBillItem()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bill item updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update bill item"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteBillItem(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billItemApi.deleteBillItem()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bill item deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete bill item"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}