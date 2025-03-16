package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.InventoryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InventoryRepo @Inject constructor(
    private val inventoryApi: InventoryApi
) {

    suspend fun getAllItems(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.getAllItems()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Items retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve items"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun addItem(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.addItem()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Item added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add item"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateItem(id: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.updateItem(id)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Item updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update item"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteItem(id: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.deleteItem(id)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Item deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete item"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getItem(id: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.getItem(id)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Item retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve item"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getItemByCategory(id: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.getItemsByCategory(id)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Item got successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete item"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}