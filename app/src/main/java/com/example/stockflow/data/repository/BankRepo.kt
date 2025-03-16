package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.BankApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BankRepo @Inject constructor(
    private val bankApi: BankApi
) {

    suspend fun getBank(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = bankApi.getBank(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bank details retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve bank details"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateBank(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = bankApi.updateBank(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Bank updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update bank"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

}