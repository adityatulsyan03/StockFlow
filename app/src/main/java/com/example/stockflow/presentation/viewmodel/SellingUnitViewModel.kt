package com.example.stockflow.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.SellingUnit
import com.example.stockflow.data.repository.SellingUnitRepo
import com.example.stockflow.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellingUnitViewModel @Inject constructor(
    private val sellingUnitRepo: SellingUnitRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _sellingUnitsState = MutableStateFlow<UiState<CustomResponse<List<SellingUnit>>>>(UiState.Idle)
    val sellingUnitsState = _sellingUnitsState.asStateFlow()

    private val _addSellingUnitState = MutableStateFlow<UiState<CustomResponse<List<SellingUnit>>>>(UiState.Idle)
    val addSellingUnitState = _addSellingUnitState.asStateFlow()

    private val _deleteSellingUnitState = MutableStateFlow<UiState<CustomResponse<Unit>>>(UiState.Idle)
    val deleteSellingUnitState = _deleteSellingUnitState.asStateFlow()

    fun resetAddSellingUnit(){
        _addSellingUnitState.value = UiState.Idle
    }

    fun getAllSellingUnits() {
        _sellingUnitsState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("SellingUnitViewModel", "Token: $token")

                sellingUnitRepo.getAllSellingUnits(token).collect { response ->
                    _sellingUnitsState.value = response
                    if (response is UiState.Success) {
                        Log.d("SellingUnitViewModel", "Selling units retrieved successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("SellingUnitViewModel", "Error fetching selling units: ${e.message}")
                _sellingUnitsState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun postSellingUnit(sellingUnit: List<SellingUnit>) {
        _addSellingUnitState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("SellingUnitViewModel", "Token: $token")

                sellingUnitRepo.postSellingUnit(token, sellingUnit).collect { response ->
                    _addSellingUnitState.value = response
                    if (response is UiState.Success) {
                        Log.d("SellingUnitViewModel", "Selling unit added successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("SellingUnitViewModel", "Error adding selling unit: ${e.message}")
                _addSellingUnitState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun deleteSellingUnit(unitId: String) {
        _deleteSellingUnitState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("SellingUnitViewModel", "Token: $token")

                sellingUnitRepo.deleteSellingUnit(token, unitId).collect { response ->
                    _deleteSellingUnitState.value = response
                    if (response is UiState.Success) {
                        Log.d("SellingUnitViewModel", "Selling unit deleted successfully")
                    }
                }
            } catch (e: Exception) {
                Log.e("SellingUnitViewModel", "Error deleting selling unit: ${e.message}")
                _deleteSellingUnitState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }
}