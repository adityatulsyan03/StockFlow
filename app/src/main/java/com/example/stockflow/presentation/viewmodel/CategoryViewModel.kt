package com.example.stockflow.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Category
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.repository.CategoryRepo
import com.example.stockflow.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepo: CategoryRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _addCategoryState =
        MutableStateFlow<UiState<CustomResponse<List<Category>>>>(UiState.Idle)
    val addCategoryState = _addCategoryState.asStateFlow()

    private val _updateCategoryState =
        MutableStateFlow<UiState<CustomResponse<Category>>>(UiState.Idle)
    val updateCategoryState = _updateCategoryState.asStateFlow()

    private val _deleteCategoryState = MutableStateFlow<UiState<CustomResponse<Unit>>>(UiState.Idle)
    val deleteCategoryState = _deleteCategoryState.asStateFlow()

    private val _getCategoriesState = MutableStateFlow<UiState<CustomResponse<List<Category>>>>(UiState.Idle)
    val getCategoriesState = _getCategoriesState.asStateFlow()

    fun resetAddCategoryState() {
        _addCategoryState.value = UiState.Idle
    }

    fun resetUpdateCategoryState(){
        _updateCategoryState.value = UiState.Idle
    }

    fun resetDeleteCategoryState(){
        _deleteCategoryState.value = UiState.Idle
    }

    fun addCategory(category: List<Category>) {
        _addCategoryState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("CategoryViewModel", "Token: $token")

                categoryRepo.addCategory(token, category).collect { response ->
                    _addCategoryState.value = response
                    if (response is UiState.Success) {
                        Log.d("CategoryViewModel", "Category added successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CategoryViewModel", "Error adding category: ${e.message}")
                _addCategoryState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun updateCategoryById(categoryId: String, category: Category) {
        _updateCategoryState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("CategoryViewModel", "Token: $token")

                categoryRepo.updateCategoryById(token, categoryId, category).collect { response ->
                    _updateCategoryState.value = response
                    if (response is UiState.Success) {
                        Log.d(
                            "CategoryViewModel",
                            "Category updated successfully: ${response.data}"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error updating category: ${e.message}")
                _updateCategoryState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun deleteCategoryById(categoryId: String) {
        _deleteCategoryState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("CategoryViewModel", "Token: $token")

                categoryRepo.deleteCategoryById(token, categoryId).collect { response ->
                    _deleteCategoryState.value = response
                    if (response is UiState.Success) {
                        Log.d("CategoryViewModel", "Category deleted successfully")
                    }
                }
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error deleting category: ${e.message}")
                _deleteCategoryState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getAllCategoriesByType(type: String) {
        _getCategoriesState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("CategoryViewModel", "Token: $token")

                categoryRepo.getAllCategoriesByType(token, type).collect { response ->
                    _getCategoriesState.value = response
                    if (response is UiState.Success) {
                        Log.d(
                            "CategoryViewModel",
                            "Categories retrieved successfully: ${response.data}"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching categories: ${e.message}")
                _getCategoriesState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getAllCategories() {
        _getCategoriesState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("CategoryViewModel", "Token: $token")

                categoryRepo.getAllCategories(token).collect { response ->
                    _getCategoriesState.value = response
                    if (response is UiState.Success) {
                        Log.d(
                            "CategoryViewModel",
                            "Categories retrieved successfully: ${response.data}"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching categories: ${e.message}")
                _getCategoriesState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }
}