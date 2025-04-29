package com.example.stockflow.presentation.screens.user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bank
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.User
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.viewmodel.UserDetailViewModel

@Composable
fun EditUserScreen(
    navController: NavController,
    viewModel: UserDetailViewModel
) {
    val userState = viewModel.getUserState.collectAsState().value
    val bankState = viewModel.bankState.collectAsState().value
    var user by remember { mutableStateOf<User?>(null) }
    var bank by remember { mutableStateOf<Bank?>(null) }
    val updateUserState by viewModel.updateUserState.collectAsState()
    val updateBankState by viewModel.updateBankState.collectAsState()

    LaunchedEffect(updateUserState is UiState.Success,updateBankState is UiState.Success){
        if (updateUserState is UiState.Success && updateBankState is UiState.Success){
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("refreshUserDetails", true)
            navController.popBackStack()
            viewModel.resetUpdateUserState()
            viewModel.resetUpdateBankState()
        }
    }

    // Fetch user and bank data **only if not already loaded**
    LaunchedEffect(Unit) {
        if (userState !is UiState.Success) {
            Log.d("User Screen userState","Success")
            viewModel.getUserData()
        }
        if (bankState !is UiState.Success) {
            Log.d("User Screen bankState","Success")
            viewModel.getBankDetails()
        }
    }

    LaunchedEffect(userState) {
        if (userState is UiState.Success) {
            Log.d("UserScreen", "Updating user from ViewModel state")
            user = userState.data.data
        }
    }

    LaunchedEffect(bankState) {
        if (bankState is UiState.Success) {
            Log.d("UserScreen", "Updating bank from ViewModel state")
            bank = bankState.data.data
        }
    }

    AppScaffold(
        topBar = {
            TopBar(
                title = "Edit User",
                navigationIcon = Icons.Default.ArrowBackIosNew,
                onNavigationClick = {
                    Log.d("EditUserScreen", "Navigating back.")
                    navController.popBackStack()
                },
            )
        }
    ) {
        when {
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

            user != null && bank != null -> {
                EditUserForm(navController, user!!, bank!!, viewModel)
            }
        }
    }
}

@Composable
fun EditUserForm(
    navController: NavController,
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
        CustomTextField(label = "UPI ID", value = upiId) { upiId = it }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    label: String,
    value: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value ?: "",
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFB0BEC5),
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}