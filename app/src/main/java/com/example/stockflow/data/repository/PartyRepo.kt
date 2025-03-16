package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.PartyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PartyRepo @Inject constructor(
    private val partyApi: PartyApi
) {

    suspend fun getAllParties(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.getAllParties()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Parties retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve parties"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun addParty(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.addParty()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Party added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add party"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateParty(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.updateParty()
            if (response.status) {
                emit(UiState.Success(data = response, message = "Party updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update party"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getPartyById(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.getParty(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Party retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to retrieve party"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteParty(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.deleteParty(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "Party deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete party"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

}