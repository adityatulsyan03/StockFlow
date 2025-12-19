package com.example.stockflow.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.data.repository.InventoryRepo
import com.example.stockflow.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val inventoryRepo: InventoryRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _addInventoryState = MutableStateFlow<UiState<CustomResponse<Inventory>>>(UiState.Idle)
    val addInventoryState = _addInventoryState.asStateFlow()

    private val _updateInventoryState = MutableStateFlow<UiState<CustomResponse<Inventory>>>(UiState.Idle)
    val updateInventoryState = _updateInventoryState.asStateFlow()

    private val _deleteInventoryState = MutableStateFlow<UiState<CustomResponse<Unit>>>(UiState.Idle)
    val deleteInventoryState = _deleteInventoryState.asStateFlow()

    private val _getInventoryByIdState = MutableStateFlow<UiState<CustomResponse<Inventory>>>(UiState.Idle)
    val getInventoryByIdState = _getInventoryByIdState.asStateFlow()

    private val _getAllInventoriesState = MutableStateFlow<UiState<CustomResponse<List<Inventory>>>>(UiState.Idle)
    val getAllInventoriesState = _getAllInventoriesState.asStateFlow()

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

    fun resetGetInventoryByIdState() {
        _getInventoryByIdState.value = UiState.Idle
    }

    fun resetGetAllInventoriesState() {
        _getAllInventoriesState.value = UiState.Idle
    }

    fun resetAddItemState() {
        _addInventoryState.value = UiState.Idle
    }

    fun resetUpdateItemState() {
        _updateInventoryState.value = UiState.Idle
    }

    fun resetDeleteItemState() {
        _deleteInventoryState.value = UiState.Idle
    }

    fun addInventory(inventory: Inventory) {
        _addInventoryState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("InventoryViewModel", "Token: $token")

                inventoryRepo.addInventory(token, inventory).collect { response ->
                    _addInventoryState.value = response
                    if (response is UiState.Success) {
                        Log.d("InventoryViewModel", "Inventory added successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("InventoryViewModel", "Error adding inventory: ${e.message}")
                _addInventoryState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun updateInventoryById(inventoryId: String, inventory: Inventory) {
        _updateInventoryState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("InventoryViewModel", "Token: $token")

                inventoryRepo.updateInventoryById(token, inventoryId, inventory).collect { response ->
                    _updateInventoryState.value = response
                    if (response is UiState.Success) {
                        Log.d("InventoryViewModel", "Inventory updated successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("InventoryViewModel", "Error updating inventory: ${e.message}")
                _updateInventoryState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun deleteInventoryById(inventoryId: String) {
        _deleteInventoryState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("InventoryViewModel", "Token: $token")

                inventoryRepo.deleteInventoryById(token, inventoryId).collect { response ->
                    _deleteInventoryState.value = response
                    if (response is UiState.Success) {
                        Log.d("InventoryViewModel", "Inventory deleted successfully")
                    }
                }
            } catch (e: Exception) {
                Log.e("InventoryViewModel", "Error deleting inventory: ${e.message}")
                _deleteInventoryState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getInventoryById(inventoryId: String) {
        _getInventoryByIdState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("InventoryViewModel", "Token: $token")

                inventoryRepo.getInventoryById(token, inventoryId).collect { response ->
                    _getInventoryByIdState.value = response
                    if (response is UiState.Success) {
                        Log.d("InventoryViewModel", "Inventory retrieved successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("InventoryViewModel", "Error fetching inventory: ${e.message}")
                _getInventoryByIdState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getAllInventories() {
        _getAllInventoriesState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("InventoryViewModel", "Token: $token")

                inventoryRepo.getAllInventories(token).collect { response ->
                    _getAllInventoriesState.value = response
                    if (response is UiState.Success) {
                        Log.d("InventoryViewModel", "All inventories retrieved successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("InventoryViewModel", "Error fetching inventories: ${e.message}")
                _getAllInventoriesState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }
}