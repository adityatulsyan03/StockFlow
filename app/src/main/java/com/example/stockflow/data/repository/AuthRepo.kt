package com.example.stockflow.data.repository

import android.util.Log
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.remote.AuthApi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val authApi: AuthApi,
    private val auth: FirebaseAuth
) {

    suspend fun verifyToken(
        accessToken: String
    ): Flow<UiState<CustomResponse<String>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response = authApi.verifyToken(accessToken)

                if (response.status) {
                    emit(UiState.Success(data = response, message = "login success"))
                } else {
                    emit(UiState.Failed(response.message ?: ""))
                }
            } catch (e: Exception) {
                emit(UiState.Failed(e.message ?: "An unexpected error occurred"))
            }
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
