package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bills
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.BillsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class BillsRepo @Inject constructor(
    private val billsApi: BillsApi
) {

    suspend fun getAllBills(token: String): Flow<UiState<CustomResponse<List<Bills>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.getAllBills(token)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Bills fetched successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch bills"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun postBill(token: String, bill: Bills): Flow<UiState<CustomResponse<Bills>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.postBill(token, bill)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Bill posted successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to post bill"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getBill(token: String, billId: String): Flow<UiState<CustomResponse<Bills>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.getBill(token, billId)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Bill fetched successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch bill"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteBill(token: String, billId: String): Flow<UiState<CustomResponse<Unit>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.deleteBill(token, billId)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Bill deleted successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to delete bill"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getBillsByParty(token: String, partyId: String): Flow<UiState<CustomResponse<List<Bills>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.getBillsByParty(token, partyId)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Bills for party fetched successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch bills for party"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getBillsByDateRange(token: String, startDate: LocalDate, endDate: LocalDate): Flow<UiState<CustomResponse<List<Bills>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = billsApi.getBillsByDateRange(token, startDate, endDate)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Bills within date range fetched successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch bills by date range"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }
}