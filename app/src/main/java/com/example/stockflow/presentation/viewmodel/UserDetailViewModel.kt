package com.example.stockflow.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.AccessTokenBody
import com.example.stockflow.data.model.Bank
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.User
import com.example.stockflow.data.repository.BankRepo
import com.example.stockflow.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val bankRepo: BankRepo
): ViewModel() {

    private val _adduser: MutableStateFlow<UiState<CustomResponse<User>>> = MutableStateFlow(UiState.Idle)
    val adduser = _adduser.asStateFlow()

    private val _getUserState = MutableStateFlow<UiState<CustomResponse<User>>>(UiState.Idle)
    val getUserState = _getUserState.asStateFlow()

    private val _deleteUserState = MutableStateFlow<UiState<CustomResponse<Unit>>>(UiState.Idle)
    val deleteUserState = _deleteUserState.asStateFlow()

    private val _updateUserState = MutableStateFlow<UiState<CustomResponse<User>>>(UiState.Idle)
    val updateUserState = _updateUserState.asStateFlow()

    private val _bankState = MutableStateFlow<UiState<CustomResponse<Bank>>>(UiState.Idle)
    val bankState = _bankState.asStateFlow()

    private val _updateBankState = MutableStateFlow<UiState<CustomResponse<Bank>>>(UiState.Idle)
    val updateBankState = _updateBankState.asStateFlow()

    fun resetAddUserState(){
        _adduser.value = UiState.Idle
    }

    fun resetGetUserState(){
        _getUserState.value = UiState.Idle
    }

    fun resetDeleteUserState(){
        _deleteUserState.value = UiState.Idle
    }

    fun resetUpdateUserState(){
        _updateUserState.value = UiState.Idle
    }

    fun resetBankState(){
        _bankState.value = UiState.Idle
    }

    fun resetUpdateBankState(){
        _updateBankState.value = UiState.Idle
    }

    fun addUser(accessToken: AccessTokenBody){
        _adduser.value = UiState.Loading

        viewModelScope.launch {
            try {
                userRepo.addUser(accessToken).collect { response ->
                    _adduser.value = response
                    if (response is UiState.Success) {
                        Log.d("LoginViewModel", "Token verified successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Token verification failed: ${e.message}")
                _adduser.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getUserData() {

        _getUserState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("UserDetailViewModel", "Fetching user data with token: $token")
                userRepo.getUser(token).collect { response ->
                    _getUserState.value = response
                }
            } catch (e: Exception) {
                _getUserState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun deleteUser() {
        _deleteUserState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("LoginViewModel", "Token: $token")
                userRepo.deleteUser(token).collect { response ->
                    _deleteUserState.value = response
                }
            } catch (e: Exception) {
                _deleteUserState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun updateUser(user: User) {
        _updateUserState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("UserDetailViewModel", "Updating user data with token: $token")
                userRepo.updateUser(token, user).collect { response ->
                    _updateUserState.value = response
                }
            } catch (e: Exception) {
                _updateUserState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getBankDetails() {
        _bankState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("BankViewModel", "Fetching bank data with token: $token")

                bankRepo.getBank(token).collect { response ->
                    _bankState.value = response
                    if (response is UiState.Success) {
                        Log.d("BankViewModel", "Bank data fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("BankViewModel", "Error fetching bank data: ${e.message}")
                _bankState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun updateBank(bank: Bank) {
        _updateBankState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("BankViewModel", "Updating bank data with token: $token")

                bankRepo.updateBank(token, bank).collect { response ->
                    _updateBankState.value = response
                    if (response is UiState.Success) {
                        Log.d("BankViewModel", "Bank updated successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("BankViewModel", "Error updating bank: ${e.message}")
                _updateBankState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun logout() {
        _adduser.value = UiState.Idle
        _getUserState.value = UiState.Idle
        _deleteUserState.value = UiState.Idle
        _updateUserState.value = UiState.Idle
        _bankState.value = UiState.Idle
        _updateBankState.value = UiState.Idle
    }

}