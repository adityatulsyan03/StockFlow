package com.example.stockflow.data.repository

import android.util.Log
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.Party
import com.example.stockflow.data.remote.PartyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PartyRepo @Inject constructor(
    private val partyApi: PartyApi
) {

    suspend fun getPartyById(token: String, partyId: String): Flow<UiState<CustomResponse<Party>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.getPartyById(token, partyId)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Party data retrieved successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch party details"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updatePartyById(token: String, partyId: String, party: Party): Flow<UiState<CustomResponse<Party>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.updatePartyById(token, partyId, party)
            if (response.status == "OK") {
                emit(UiState.Success(response, "Party updated successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to update party"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deletePartyById(token: String, partyId: String): Flow<UiState<CustomResponse<Unit>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.deletePartyById(token, partyId)
            if (response.status == "OK") {
                Log.d("deletePartyById Success","Party deleted successfully")
                emit(UiState.Success(response, "Party deleted successfully"))
            } else {
                Log.d("deletePartyById Else",response.message ?: "Failed to delete party")
                emit(UiState.Failed(response.message ?: "Failed to delete party"))
            }
        } catch (e: Exception) {
            Log.d("deletePartyById E",e.message ?: "An unexpected error occurred")
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getAllParties(token: String): Flow<UiState<CustomResponse<List<Party>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.getAllParties(token)
            if (response.status == "OK") {
                emit(UiState.Success(response, "All parties retrieved successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to fetch parties"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun createParty(token: String, party: Party): Flow<UiState<CustomResponse<Party>>> = flow {
        try {
            emit(UiState.Loading)
            val response = partyApi.createParty(token, party)
            if (response.status == "CREATED") {
                emit(UiState.Success(response, "Party created successfully"))
            } else {
                emit(UiState.Failed(response.message ?: "Failed to create party"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
        }
    }
}