package com.example.stockflow.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bank
import com.example.stockflow.data.model.User
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.components.UserDetailItem
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.viewmodel.UserDetailViewModel
import com.example.stockflow.ui.theme.StockFlowTheme

@Composable
fun UserScreen(
    navController: NavController,
    viewModel: UserDetailViewModel
) {

    val userState = viewModel.getUserState.collectAsState().value
    val bankState = viewModel.bankState.collectAsState().value

    StockFlowTheme {
        AppScaffold(
            topBar = {
                TopBar(
                    title = "User Details",
                    navigationIcon = Icons.Outlined.ArrowBackIosNew,
                    onNavigationClick = { navController.popBackStack() },
                    navigationIconContentDescription = "Back",
                    onTitleClick = {
                        navController.navigate(Screens.UserScreen.route)
                    },
                    trailingIcon = Icons.Default.Edit,
                    trailingIconContentDescription = "Edit",
                    onTrailingClick = {
                        navController.navigate(Screens.EditUserScreen.route)
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (userState){

                    is UiState.Idle -> {
                        viewModel.getUserData()
                    }

                    is UiState.Success -> {
                        val user = userState.data.data
                        Log.d("User Data",user.toString())
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(user?.profilePhoto)
                                    .crossfade(true)
                                    .build()
                            ),
                            contentDescription = "Profile Photo",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        val details = listOf(
                            Pair("Business Name", user?.businessName ?: "N/A"),
                            Pair("Contact Name", user?.contactName ?: "N/A"),
                            Pair("Contact Number", user?.contactNumber ?: "N/A"),
                            Pair("Email", user?.email ?: "N/A"),
                            Pair("Industry", user?.industry ?: "N/A"),
                            Pair("GST Number", user?.gstNumber ?: "N/A"),
                            Pair("License Number", user?.licenseNumber ?: "N/A"),
                            Pair("Address", user?.address ?: "N/A"),
                            Pair("Postal Code", user?.postalCode.toString())
                        )

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)), // Dark card
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                details.forEach {
                                    UserDetailItem(
                                        label = it.first, value = it.second
                                    )
                                }
                            }
                        }
                    }

                    is UiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is UiState.Failed -> {
                        Text(
                            text = "Failed to load User details",
                            fontSize = 16.sp
                        )
                    }

                }

                when (bankState){

                    is UiState.Idle -> {
                        viewModel.getUserData()
                    }

                    is UiState.Success -> {

                        val bank = bankState.data.data

                        val details = listOf(
                            Pair("Bank Account Number", bank?.bankAccountNumber ?: "N/A"),
                            Pair("IFSC Code", bank?.ifscCode ?: "N/A"),
                            Pair("Account Holder Name", bank?.accountHolderName ?: "N/A"),
                            Pair("Bank Name", bank?.bankName ?: "N/A"),
                            Pair("UPI ID", bank?.upiId ?: "N/A")
                        )

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)), // Dark card
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                details.forEach {
                                    UserDetailItem(
                                        label = it.first, value = it.second
                                    )
                                }
                            }
                        }
                    }

                    is UiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is UiState.Failed -> {
                        Text(
                            text = "Failed to load User details",
                            fontSize = 16.sp
                        )
                    }

                }
            }
        }
    }
}
