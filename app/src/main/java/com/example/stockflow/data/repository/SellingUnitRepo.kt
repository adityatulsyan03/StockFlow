package com.example.stockflow.data.repository

import android.util.Log
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.SellingUnit
import com.example.stockflow.data.remote.SellingUnitApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SellingUnitRepo @Inject constructor(
    private val sellingUnitApi: SellingUnitApi
) {

    suspend fun getAllSellingUnits(token: String): Flow<UiState<CustomResponse<List<SellingUnit>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = sellingUnitApi.getAllSellingUnits(token)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Selling units retrieved successfully"))
                Log.d("SellingUnitRepo", response.data.toString())
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to fetch selling units"))
                Log.d("SellingUnitRepo", response.message.toString())
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
            Log.d("SellingUnitRepo", e.message.toString())
        }
    }

    suspend fun postSellingUnit(token: String, sellingUnit: List<SellingUnit>): Flow<UiState<CustomResponse<List<SellingUnit>>>> = flow {
        try {
            emit(UiState.Loading)
            val response = sellingUnitApi.postSellingUnit(token, sellingUnit)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Selling unit added successfully"))
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to add selling unit"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteSellingUnit(token: String, unitId: String): Flow<UiState<CustomResponse<Unit>>> = flow {
        try {
            emit(UiState.Loading)
            val response = sellingUnitApi.deleteSellingUnit(token, unitId)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "Selling unit deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to delete selling unit"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}