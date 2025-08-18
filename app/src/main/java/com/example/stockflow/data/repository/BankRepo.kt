package com.example.stockflow.data.repository

import android.util.Log
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bank
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.BankApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BankRepo @Inject constructor(
    private val bankApi: BankApi
) {

    suspend fun getBank(token: String): Flow<UiState<CustomResponse<Bank>>> = flow {
        try {
            emit(UiState.Loading)
            val response = bankApi.getBank(token)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Bank data retrieved successfully"))
                Log.d("Bank Data OK", response.data.toString())
            } else {
                emit(UiState.Failed(message = response.message ?: "An unexpected error occurred"))
                Log.d("Bank Data Failed", response.message.toString())
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
            Log.d("Bank Data exception", e.message.toString())
        }
    }

    suspend fun updateBank(token: String, bank: Bank): Flow<UiState<CustomResponse<Bank>>> = flow {
        try {
            emit(UiState.Loading)
            val response = bankApi.updateBank(token, bank)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Bank updated successfully"))
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to update bank"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}