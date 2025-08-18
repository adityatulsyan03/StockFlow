package com.example.stockflow.data.repository

import android.util.Log
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.AccessTokenBody
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.User
import com.example.stockflow.data.remote.UserApi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val userApi: UserApi,
    private val auth: FirebaseAuth
) {

    suspend fun getUser(token: String): Flow<UiState<CustomResponse<User>>> = flow {
        try {
            emit(UiState.Loading)
            val response = userApi.getUser(token)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "User data retrieved successfully"))
                Log.d("User Data", response.data.toString())
            } else {
                emit(UiState.Failed(message = response.message ?: "An unexpected error occurred"))
                Log.d("User Data", response.message.toString())
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
            Log.d("User Data", e.message.toString())
        }
    }

    suspend fun updateUser(token: String,user: User): Flow<UiState<CustomResponse<User>>> = flow {
        try {
            emit(UiState.Loading)
            val response = userApi.updateUser(token,user)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "User updated successfully"))
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to update user"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun deleteUser(token: String): Flow<UiState<CustomResponse<Unit>>> = flow {
        try {
            emit(UiState.Loading)
            val response = userApi.deleteUser(token)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "User deleted successfully"))
                Log.d("User Data", response.data.toString())
                
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to delete user"))
                Log.d("User Data", response.message.toString())
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
            Log.d("User Data", e.message.toString())
        }
    }

    suspend fun addUser(
        token: AccessTokenBody
    ): Flow<UiState<CustomResponse<User>>> = flow {
        try {
            emit(UiState.Loading)
            val response = userApi.authUser(token)
            if (response.status == "OK") {
                emit(UiState.Success(data = response, message = "User added successfully"))
                Log.d("User Data", response.data.toString())
            } else {
                emit(UiState.Failed(message = response.message ?: "Failed to add user"))
                Log.d("User Data", response.message.toString())
            }
        } catch (e: HttpException) {
            if (e.code() == 409) {
                // User already exists, treat as successful login
                emit(UiState.Success(data = CustomResponse("OK", "User login successful", null), message = "User login successful"))
                Log.d("User Data", "User already exists (409), proceeding with login")
            } else {
                emit(UiState.Failed(message = "HTTP ${e.code()}: ${e.message()}"))
                Log.d("User Data", "HTTP ${e.code()}: ${e.message()}")
            }
        } catch (e: Exception) {
            emit(UiState.Failed(message = e.message ?: "An unexpected error occurred"))
            Log.d("User Data", e.message.toString())
        }
    }

    suspend fun getIdToken(): String {
        return try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val idTokenResult = currentUser.getIdToken(true).await()
                val token = idTokenResult?.token ?: "No Token Found"
                Log.d("AuthRepo", token)
                "Bearer $token"
            } else {
                "No Token Found"
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error getting token", e)
            "No Token Found"
        }
    }
}