package com.example.stockflow.presentation.screens.reports

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.viewmodel.BillsViewModel
import com.example.stockflow.utils.safePopBackStack
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BillsScreen(
    navController: NavController,
    viewModel: BillsViewModel
) {

    Log.d("Screen","Bill Screen")

    val billState by viewModel.billsState.collectAsState()

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle?.get<Boolean>("refreshBill")) {
        val shouldRefresh = savedStateHandle?.get<Boolean>("refreshBill") ?: false
        if (shouldRefresh) {
            Log.d("Launched Effect","savedStateHandle")
//            viewModel.getAllBills()
            savedStateHandle.remove<Boolean>("refreshBill")
        }
    }

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            viewModel.getAllBills()
            isRefreshing = false
        }
    }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Bills",
                navigationIcon = Icons.Default.ArrowBackIosNew,
                navigationIconContentDescription = "Back",
                onNavigationClick = {
                    navController.safePopBackStack()
                }
            )
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { isRefreshing = true }
        ){
            when (billState) {

                is UiState.Idle -> {}

                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp),
                        color = Color.White
                    )
                }

                is UiState.Success -> {
                    val bills = (billState as UiState.Success).data.data ?: emptyList()
                    if (bills.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No items found", color = Color.Gray)
                        }
                    } else {
                        Log.d("BillsScreen", bills.toString())
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            LazyColumn {
                                items(bills) { bill ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        BillCard(
                                            name = bill.partyName,
                                            date = bill.billDate,
                                            amount = bill.totalAmount.toString(),
                                            isInvoice = bill.isInvoice,
                                            onClick = {
                                                val gson = Gson()
                                                val billJson = gson.toJson(bill)
                                                val encodedBillJson = URLEncoder.encode(
                                                    billJson,
                                                    StandardCharsets.UTF_8.toString()
                                                )

                                                navController.navigate("${Screens.BillDetailsScreen.route}/$encodedBillJson")
                                                Log.d("BillsScreen", bill.id?:"No Id Found")
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is UiState.Failed -> {
                    val errorMessage = (billState as UiState.Failed).message
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error: $errorMessage", color = Color.Red)
                    }
                }

            }
        }
    }
}

@Composable
fun BillCard(
    name: String,
    date: String,
    amount: String,
    isInvoice: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1E1E)) // Dark theme background
            .clickable { onClick.invoke() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = date,
                color = Color(0xFFB0BEC5) // Light gray for balance
            )
        }

        Text(
            text = amount,
            fontWeight = FontWeight.Bold,
            color = if (isInvoice) Color.Green else Color.Red// Keeping amount in contrast color
        )
    }
}