package com.example.stockflow.data.repository

import android.util.Log
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.data.remote.InventoryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InventoryRepo @Inject constructor(
    private val inventoryApi: InventoryApi
) {

    suspend fun getInventoryById(token: String, inventoryId: String): Flow<UiState<CustomResponse<Inventory>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.getInventoryById(token, inventoryId)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Inventory retrieved successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch inventory"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateInventoryById(token: String, inventoryId: String, inventory: Inventory): Flow<UiState<CustomResponse<Inventory>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.updateInventoryById(token, inventoryId, inventory)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Inventory updated successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to update inventory"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteInventoryById(token: String, inventoryId: String): Flow<UiState<CustomResponse<Unit>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.deleteInventoryById(token, inventoryId)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Inventory deleted successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to delete inventory"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getAllInventories(token: String): Flow<UiState<CustomResponse<List<Inventory>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.getAllInventories(token)
            if (response.status == "OK") {
                emit(UiState.Success(response, "All inventories retrieved successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch inventories"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun addInventory(token: String, inventory: Inventory): Flow<UiState<CustomResponse<Inventory>>> = flow {
        try {
            emit(UiState.Loading)
            val response = inventoryApi.addInventory(token, inventory)
            if (response.status == "CREATED") {
                emit(UiState.Success(response, "Inventory added successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to add inventory"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }
}