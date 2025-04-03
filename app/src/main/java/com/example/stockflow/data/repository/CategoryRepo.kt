package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Category
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.CategoryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepo @Inject constructor(
    private val categoryApi: CategoryApi
) {

    suspend fun updateCategoryById(token: String, categoryId: String, category: Category): Flow<UiState<CustomResponse<Category>>> = flow {
        try {
            emit(UiState.Loading)
            val response = categoryApi.updateCategoryById(token, categoryId, category)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Category updated successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to update category"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteCategoryById(token: String, categoryId: String): Flow<UiState<CustomResponse<Unit>>> = flow {
        try {
            emit(UiState.Loading)
            val response = categoryApi.deleteCategoryById(token, categoryId)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Category deleted successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to delete category"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getAllCategoriesByType(token: String, type: String): Flow<UiState<CustomResponse<List<Category>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = categoryApi.getAllCategoriesByType(token, type)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Categories retrieved successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch categories"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getAllCategories(token: String): Flow<UiState<CustomResponse<List<Category>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = categoryApi.getAllCategories(token)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Categories retrieved successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch categories"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun addCategory(token: String, category: List<Category>): Flow<UiState<CustomResponse<List<Category>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = categoryApi.addCategory(token, category)
            if (response.status == "CREATED") {
                emit(UiState.Success(response, "Category added successfully"))

            } else {
                emit(UiState.Failed(response.message ?: "Failed to add category"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }
}