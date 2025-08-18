package com.example.stockflow.presentation.screens.user

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
import androidx.compose.runtime.*
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
import com.example.stockflow.utils.safePopBackStack
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun UserScreen(
    navController: NavController,
    viewModel: UserDetailViewModel
) {

    Log.d("Screen","User Screen")

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            viewModel.getUserData()
            viewModel.getBankDetails()
            isRefreshing = false
        }
    }

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle?.get<Boolean>("refreshUserDetails")) {
        val shouldRefresh = savedStateHandle?.get<Boolean>("refreshUserDetails") ?: false
        if (shouldRefresh) {
            Log.d("Launched Effect","savedStateHandle")
            viewModel.getUserData()
            viewModel.getBankDetails()
            savedStateHandle.remove<Boolean>("refreshUserDetails")
        }
    }

    val userState = viewModel.getUserState.collectAsState().value
    val bankState = viewModel.bankState.collectAsState().value

    StockFlowTheme {
        AppScaffold(
            topBar = {
                TopBar(
                    title = "User Details",
                    navigationIcon = Icons.Outlined.ArrowBackIosNew,
                    onNavigationClick = { navController.safePopBackStack() },
                    navigationIconContentDescription = "Back",
                    trailingIcon = Icons.Default.Edit,
                    trailingIconContentDescription = "Edit",
                    onTrailingClick = {
                        navController.navigate(Screens.EditUserScreen.route)
                    }
                )
            }
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { isRefreshing = true }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (userState) {

                        is UiState.Idle -> {
                            viewModel.getUserData()
                        }

                        is UiState.Success -> {

                            val user = userState.data.data

                            Log.d("User Data", user?.toString() ?: "No user data")
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

                            Text(
                                text = "User Details",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 8.dp)
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

                    // Label for Bank Details
                    Text(
                        text = "Bank Details",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    when (bankState) {

                        is UiState.Idle -> {
                            viewModel.getBankDetails()
                        }

                        is UiState.Success -> {

                            val bankData = bankState.data.data

                            val details = listOf(
                                Pair("Bank Account Number", bankData?.bankAccountNumber ?: "N/A"),
                                Pair("IFSC Code", bankData?.ifscCode ?: "N/A"),
                                Pair("Account Holder Name", bankData?.accountHolderName ?: "N/A"),
                                Pair("Bank Name", bankData?.bankName ?: "N/A"),
                                Pair("UPI ID", bankData?.upiId ?: "N/A")
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
                                text = "Failed to load Bank details",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}