package com.example.stockflow.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bills
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.repository.BillsRepo
import com.example.stockflow.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BillsViewModel @Inject constructor(
    private val billsRepo: BillsRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _billsState = MutableStateFlow<UiState<CustomResponse<List<Bills>>>>(UiState.Idle)
    val billsState = _billsState.asStateFlow()

    private val _addBillState = MutableStateFlow<UiState<CustomResponse<Bills>>>(UiState.Idle)
    val addBillState = _addBillState.asStateFlow()

    private val _deleteBillState = MutableStateFlow<UiState<CustomResponse<Unit>>>(UiState.Idle)
    val deleteBillState = _deleteBillState.asStateFlow()

    private val _billByIdState = MutableStateFlow<UiState<CustomResponse<Bills>>>(UiState.Idle)
    val billByIdState = _billByIdState.asStateFlow()

    private val _billsByPartyState = MutableStateFlow<UiState<CustomResponse<List<Bills>>>>(UiState.Idle)
    val billsByPartyState = _billsByPartyState.asStateFlow()

    private val _billsByDateRangeState = MutableStateFlow<UiState<CustomResponse<List<Bills>>>>(UiState.Idle)
    val billsByDateRangeState = _billsByDateRangeState.asStateFlow()

    fun resetGetBillState(){
        _billsState.value = UiState.Idle
    }

    fun resetBillByIdState(){
        _billByIdState.value = UiState.Idle
    }

    fun resetBillsByPartyState(){
        _billsByPartyState.value = UiState.Idle
    }

    fun resetBillsByDateRangeState(){
        _billsByDateRangeState.value = UiState.Idle
    }

    fun resetAddBillState(){
        _addBillState.value = UiState.Idle
    }

    fun resetDeleteBillState(){
        _deleteBillState.value = UiState.Idle
    }

    fun getAllBills() {
        _billsState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("BillsViewModel", "Fetching bills with token: $token")

                billsRepo.getAllBills(token).collect { response ->
                    _billsState.value = response
                    if (response is UiState.Success) {
                        Log.d("BillsViewModel", "Bills fetched: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("BillsViewModel", "Error fetching bills: ${e.message}")
                _billsState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun addBill(bill: Bills) {
        _addBillState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("BillsViewModel", "Posting bill with token: $token")

                billsRepo.postBill(token, bill).collect { response ->
                    _addBillState.value = response
                    if (response is UiState.Success) {
                        Log.d("BillsViewModel", "Bill posted successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("BillsViewModel", "Error posting bill: ${e.message}")
                _addBillState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun deleteBill(billId: String) {
        _deleteBillState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("BillsViewModel", "Deleting bill with ID: $billId")

                billsRepo.deleteBill(token, billId).collect { response ->
                    _deleteBillState.value = response
                    if (response is UiState.Success) {
                        Log.d("BillsViewModel", "Bill deleted successfully")
                    }
                }
            } catch (e: Exception) {
                Log.e("BillsViewModel", "Error deleting bill: ${e.message}")
                _deleteBillState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getBill(billId: String) {
        _billByIdState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("BillsViewModel", "Fetching bill with ID: $billId")

                billsRepo.getBill(token, billId).collect { response ->
                    _billByIdState.value = response
                    if (response is UiState.Success) {
                        Log.d("BillsViewModel", "Bill fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("BillsViewModel", "Error fetching bill: ${e.message}")
                _billByIdState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getBillsByParty(partyId: String) {
        _billsByPartyState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("BillsViewModel", "Fetching bills for party ID: $partyId")

                billsRepo.getBillsByParty(token, partyId).collect { response ->
                    _billsByPartyState.value = response
                    if (response is UiState.Success) {
                        Log.d("BillsViewModel", "Bills for party fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("BillsViewModel", "Error fetching bills by party: ${e.message}")
                _billsByPartyState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getBillsByDateRange(startDate: LocalDate, endDate: LocalDate) {
        _billsByDateRangeState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("BillsViewModel", "Fetching bills from $startDate to $endDate")

                billsRepo.getBillsByDateRange(token, startDate, endDate).collect { response ->
                    _billsByDateRangeState.value = response
                    if (response is UiState.Success) {
                        Log.d("BillsViewModel", "Bills by date range fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("BillsViewModel", "Error fetching bills by date range: ${e.message}")
                _billsByDateRangeState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }
}