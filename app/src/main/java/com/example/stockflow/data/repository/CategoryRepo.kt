package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.CategoryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepo @Inject constructor(
    private val categoryApi: CategoryApi
) {

    suspend fun getAllCategories(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = categoryApi.getAllCategories()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Categories retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data?.toString() ?: "Failed to retrieve categories"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun addCategory(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = categoryApi.addCategory(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Category added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add category"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateCategory(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = categoryApi.updateCategory(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Category updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update category"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteCategory(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = categoryApi.deleteCategory(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Category deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete category"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}