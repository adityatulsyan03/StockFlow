package com.example.stockflow.presentation.screens.user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bank
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.User
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.CustomTextField
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.viewmodel.UserDetailViewModel
import com.example.stockflow.utils.safePopBackStack

@Composable
fun EditUserScreen(
    navController: NavController,
    viewModel: UserDetailViewModel
) {
    val userState by viewModel.getUserState.collectAsState()
    val bankState by viewModel.bankState.collectAsState()
    val updateUserState by viewModel.updateUserState.collectAsState()
    val updateBankState by viewModel.updateBankState.collectAsState()

    LaunchedEffect(updateUserState,updateBankState){
        if (updateUserState is UiState.Success && updateBankState is UiState.Success){
            Log.d("EditUserScreen", "Data updated successfully.")
            navController.safePopBackStack()
            viewModel.resetUpdateUserState()
            viewModel.resetUpdateBankState()
            viewModel.resetBankState()
            viewModel.resetGetUserState()
        }
    }

    AppScaffold(
        topBar = {
            TopBar(
                title = "Edit User",
                navigationIcon = Icons.Default.ArrowBackIosNew,
                onNavigationClick = {
                    Log.d("EditUserScreen", "Navigating back.")
                    navController.safePopBackStack()
                },
            )
        }
    ) {
        when {
            userState is UiState.Idle -> {
                viewModel.getUserData()
            }
            bankState is UiState.Idle -> {
                viewModel.getBankDetails()
            }
            userState is UiState.Loading || bankState is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                    Log.d("EditUserScreen", "Loading user and bank data...")
                }
            }

            userState is UiState.Failed || bankState is UiState.Failed -> {
                Text("Failed to load data", color = Color.Red, fontSize = 18.sp)
                Log.e("EditUserScreen", "Error while loading data.")
            }

            userState is UiState.Success && bankState is UiState.Success -> {
                val user = (userState as UiState.Success<CustomResponse<User>>).data.data
                val bank = (bankState as UiState.Success<CustomResponse<Bank>>).data.data
                if(user != null && bank != null){
                    EditUserForm(user, bank, viewModel)
                }
            }

            updateUserState is UiState.Loading || updateBankState is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                    Log.d("EditUserScreen", "Updating user and bank data...")
                }
            }

            updateUserState is UiState.Failed || updateBankState is UiState.Failed -> {
                Text("Failed to update data", color = Color.Red, fontSize = 18.sp)
                Log.e("EditUserScreen", "Error while updating data.")
            }

            updateUserState  is UiState.Success && updateBankState is UiState.Success -> {
            }

            else -> {
                Log.e("EditUserScreen", "Unexpected state: $userState, $bankState")
            }
        }
    }
}

@Composable
fun EditUserForm(
    user: User,
    bank: Bank,
    viewModel: UserDetailViewModel
) {
    var businessName by remember { mutableStateOf(user.businessName) }
    var contactName by remember { mutableStateOf(user.contactName) }
    var contactNumber by remember { mutableStateOf(user.contactNumber) }
    var email by remember { mutableStateOf(user.email) }
    var gstNumber by remember { mutableStateOf(user.gstNumber) }
    var licenseNumber by remember { mutableStateOf(user.licenseNumber) }
    var address by remember { mutableStateOf(user.address) }
    var postalCode by remember { mutableStateOf(user.postalCode.toString()) }

    var bankAccountNumber by remember { mutableStateOf(bank.bankAccountNumber) }
    var ifscCode by remember { mutableStateOf(bank.ifscCode) }
    var accountHolderName by remember { mutableStateOf(bank.accountHolderName) }
    var bankName by remember { mutableStateOf(bank.bankName) }
    var upiId by remember { mutableStateOf(bank.upiId) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("User Details", fontSize = 18.sp, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
        CustomTextField(label = "Business Name", value = businessName) { businessName = it }
        CustomTextField(label = "Contact Name", value = contactName) { contactName = it }
        CustomTextField(label = "Contact Number", value = contactNumber, keyboardType = KeyboardType.Phone) { contactNumber = it }
        CustomTextField(label = "Email", value = email, keyboardType = KeyboardType.Email) { email = it }
        CustomTextField(label = "GST Number", value = gstNumber) { gstNumber = it }
        CustomTextField(label = "License Number", value = licenseNumber) { licenseNumber = it }
        CustomTextField(label = "Address", value = address) { address = it }
        CustomTextField(label = "Postal Code", value = postalCode, keyboardType = KeyboardType.Number) { postalCode = it }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Bank Details", fontSize = 18.sp, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
        CustomTextField(label = "Bank Account Number", value = bankAccountNumber, keyboardType = KeyboardType.Number) { bankAccountNumber = it }
        CustomTextField(label = "IFSC Code", value = ifscCode) { ifscCode = it }
        CustomTextField(label = "Account Holder Name", value = accountHolderName) { accountHolderName = it }
        CustomTextField(label = "Bank Name", value = bankName) { bankName = it }
        CustomTextField(label = "UPI ID", value = upiId, imeAction = ImeAction.Done) { upiId = it }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Log.d("EditUserScreen", "Saving changes for user and bank.")
                val updatedUser = user.copy(
                    businessName = businessName,
                    contactName = contactName,
                    contactNumber = contactNumber,
                    email = email,
                    gstNumber = gstNumber,
                    licenseNumber = licenseNumber,
                    address = address,
                    postalCode = postalCode.toIntOrNull() ?: 0
                )

                val updatedBank = bank.copy(
                    bankAccountNumber = bankAccountNumber,
                    ifscCode = ifscCode,
                    accountHolderName = accountHolderName,
                    bankName = bankName,
                    upiId = upiId
                )

                viewModel.updateUser(updatedUser)
                viewModel.updateBank(updatedBank)

                Log.d("EditUserScreen", "Changes saved, navigating back.")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}