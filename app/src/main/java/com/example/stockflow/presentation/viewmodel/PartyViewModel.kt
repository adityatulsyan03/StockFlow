package com.example.stockflow.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.Party
import com.example.stockflow.data.repository.PartyRepo
import com.example.stockflow.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyViewModel @Inject constructor(
    private val partyRepo: PartyRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _createPartyState = MutableStateFlow<UiState<CustomResponse<Party>>>(UiState.Idle)
    val createPartyState = _createPartyState.asStateFlow()

    private val _updatePartyState = MutableStateFlow<UiState<CustomResponse<Party>>>(UiState.Idle)
    val updatePartyState = _updatePartyState.asStateFlow()

    private val _deletePartyState = MutableStateFlow<UiState<CustomResponse<Unit>>>(UiState.Idle)
    val deletePartyState = _deletePartyState.asStateFlow()

    private val _getPartyByIdState = MutableStateFlow<UiState<CustomResponse<Party>>>(UiState.Idle)
    val getPartyByIdState = _getPartyByIdState.asStateFlow()

    private val _getAllPartiesState = MutableStateFlow<UiState<CustomResponse<List<Party>>>>(UiState.Idle)
    val getAllPartiesState = _getAllPartiesState.asStateFlow()

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

    fun resetGetPartyByIdState() {
        _getPartyByIdState.value = UiState.Idle
    }

    fun resetGetAllPartiesState() {
        _getAllPartiesState.value = UiState.Idle
    }

    fun resetCreatePartyState() {
        _createPartyState.value = UiState.Idle
    }

    fun resetUpdatePartyState() {
        _updatePartyState.value = UiState.Idle
    }

    fun resetDeletePartyState() {
        _deletePartyState.value = UiState.Idle
    }

    fun createParty(party: Party) {
        _createPartyState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("PartyViewModel", "Token: $token")

                partyRepo.createParty(token, party).collect { response ->
                    _createPartyState.value = response
                    if (response is UiState.Success) {
                        Log.d("PartyViewModel", "Party created successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("PartyViewModel", "Error creating party: ${e.message}")
                _createPartyState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun updatePartyById(partyId: String, party: Party) {
        _updatePartyState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("PartyViewModel", "Token: $token")

                partyRepo.updatePartyById(token, partyId, party).collect { response ->
                    _updatePartyState.value = response
                    if (response is UiState.Success) {
                        Log.d("PartyViewModel", "Party updated successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("PartyViewModel", "Error updating party: ${e.message}")
                _updatePartyState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun deletePartyById(partyId: String) {
        _deletePartyState.value = UiState.Loading
        Log.d("Delete Party ",partyId)
        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("PartyViewModel", "Token: $token")

                partyRepo.deletePartyById(token, partyId).collect { response ->
                    _deletePartyState.value = response
                    if (response is UiState.Success) {
                        Log.d("PartyViewModel", "Party deleted successfully")
                    }
                }
            } catch (e: Exception) {
                Log.e("PartyViewModel", "Error deleting party: ${e.message}")
                _deletePartyState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getPartyById(partyId: String) {
        _getPartyByIdState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("PartyViewModel", "Token: $token")

                partyRepo.getPartyById(token, partyId).collect { response ->
                    _getPartyByIdState.value = response
                    if (response is UiState.Success) {
                        Log.d("PartyViewModel", "Party retrieved successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("PartyViewModel", "Error fetching party: ${e.message}")
                _getPartyByIdState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getAllParties() {
        _getAllPartiesState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val token = userRepo.getIdToken()
                Log.d("PartyViewModel", "Token: $token")

                partyRepo.getAllParties(token).collect { response ->
                    _getAllPartiesState.value = response
                    if (response is UiState.Success) {
                        Log.d("PartyViewModel", "All parties retrieved successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("PartyViewModel", "Error fetching parties: ${e.message}")
                _getAllPartiesState.value = UiState.Failed(e.message ?: "Unknown error occurred")
            }
        }
    }
}