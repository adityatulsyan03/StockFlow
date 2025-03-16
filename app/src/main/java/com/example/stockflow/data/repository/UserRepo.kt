package com.example.stockflow.data.repository

import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.UserApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun getUser(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = userApi.getUser(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "User data retrieved successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "An unexpected error occurred"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun updateUser(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = userApi.updateUser(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "User updated successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to update user"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteUser(name: String): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = userApi.deleteUser(name)
            if (response.status) {
                emit(UiState.Success(data = response, message = "User deleted successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to delete user"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun addUser(): Flow<UiState<CustomResponse<String>>> = flow {
        try {
            emit(UiState.Loading)
            val response = userApi.addUser()
            if (response.status) {
                emit(UiState.Success(data = response, message = "User added successfully"))
            } else {
                emit(UiState.Failed(message = response.data ?: "Failed to add user"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }
}