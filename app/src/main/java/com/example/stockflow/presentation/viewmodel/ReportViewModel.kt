package com.example.stockflow.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.*
import com.example.stockflow.data.repository.ReportRepo
import com.example.stockflow.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepo: ReportRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _transactionsReportState = MutableStateFlow<UiState<CustomResponse<List<TransactionReport>>>>(UiState.Idle)
    val transactionsReportState = _transactionsReportState.asStateFlow()

    private val _stockReportState = MutableStateFlow<UiState<CustomResponse<List<StockReport>>>>(UiState.Idle)
    val stockReportState = _stockReportState.asStateFlow()

    private val _stockReportByInventoryState = MutableStateFlow<UiState<CustomResponse<StockReport>>>(UiState.Idle)
    val stockReportByInventoryState = _stockReportByInventoryState.asStateFlow()

    private val _moneyReportState = MutableStateFlow<UiState<CustomResponse<List<MoneyReport>>>>(UiState.Idle)
    val moneyReportState = _moneyReportState.asStateFlow()

    private val _daybookReportState = MutableStateFlow<UiState<CustomResponse<DayBookReport>>>(UiState.Idle)
    val daybookReportState = _daybookReportState.asStateFlow()

    private val _daybookReportByDateState = MutableStateFlow<UiState<CustomResponse<DayBookReport>>>(UiState.Idle)
    val daybookReportByDateState = _daybookReportByDateState.asStateFlow()

    fun getTransactionsReport(startDate: String, endDate: String) {
        _transactionsReportState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("ReportViewModel", "Token: $token")

                reportRepo.getTransactionsReport(token, startDate, endDate).collect { response ->
                    _transactionsReportState.value = response
                    if (response is UiState.Success) {
                        Log.d("ReportViewModel", "Transactions report fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error fetching transactions report: ${e.message}")
                _transactionsReportState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getStockReport() {
        _stockReportState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("ReportViewModel", "Token: $token")

                reportRepo.getStockReport(token).collect { response ->
                    _stockReportState.value = response
                    if (response is UiState.Success) {
                        Log.d("ReportViewModel", "Stock report fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error fetching stock report: ${e.message}")
                _stockReportState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getStockReportByInventoryId(inventoryId: String) {
        _stockReportByInventoryState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("ReportViewModel", "Token: $token")

                reportRepo.getStockReportByInventoryId(token, inventoryId).collect { response ->
                    _stockReportByInventoryState.value = response
                    if (response is UiState.Success) {
                        Log.d("ReportViewModel", "Stock report by inventory fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error fetching stock report by inventory: ${e.message}")
                _stockReportByInventoryState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getMoneyReport(startDate: String, endDate: String) {
        _moneyReportState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("ReportViewModel", "Token: $token")

                reportRepo.getMoneyReport(token, startDate, endDate).collect { response ->
                    _moneyReportState.value = response
                    if (response is UiState.Success) {
                        Log.d("ReportViewModel", "Money report fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error fetching money report: ${e.message}")
                _moneyReportState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getDaybookReport(startDate: String, endDate: String) {
        _daybookReportState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("ReportViewModel", "Token: $token")

                reportRepo.getDaybookReport(token, startDate, endDate).collect { response ->
                    _daybookReportState.value = response
                    if (response is UiState.Success) {
                        Log.d("ReportViewModel", "Daybook report fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error fetching daybook report: ${e.message}")
                _daybookReportState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getDaybookReportByDate(date: String) {
        _daybookReportByDateState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("ReportViewModel", "Token: $token")

                reportRepo.getDaybookReportByDate(token, date).collect { response ->
                    _daybookReportByDateState.value = response
                    if (response is UiState.Success) {
                        Log.d("ReportViewModel", "Daybook report by date fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error fetching daybook report by date: ${e.message}")
                _daybookReportByDateState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }
}