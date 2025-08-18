package com.example.stockflow.presentation.screens.reports

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bills
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.screens.user.LoadingScreen
import com.example.stockflow.presentation.viewmodel.BillsViewModel
import com.example.stockflow.utils.safePopBackStack

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BillDetailsScreen(
    navController: NavController,
    viewModel: BillsViewModel,
    bill: Bills
) {
    Log.d("Screen","Bill Detail Screen")

    val deleteBillState by viewModel.deleteBillState.collectAsState()

    when(deleteBillState){
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Success -> {
            Log.d("Add Bill","UiState Success")
            viewModel.getAllBills()
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("refreshBill", true)
            navController.safePopBackStack()
            viewModel.resetDeleteBillState()
        }
        else -> {}
    }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Bill Details",
                navigationIcon = Icons.Default.ArrowBackIosNew,
                navigationIconContentDescription = "Back",
                onNavigationClick = {
                    navController.safePopBackStack()
                },
                trailingIcon = Icons.Default.Delete,
                trailingIconContentDescription = "Delete",
                onTrailingClick = {
                    viewModel.deleteBill(billId = bill.id ?: "")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Date: ${bill.billDate}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            Text("Time: ${bill.billTime}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            Text("Party: ${bill.partyName}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            Text("Payment: ${bill.paymentMethod}", style = MaterialTheme.typography.bodyLarge, color = Color.LightGray)
            Text("Total: ₹${bill.totalAmount}", style = MaterialTheme.typography.bodyLarge, color = Color.Green)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Items:", style = MaterialTheme.typography.titleMedium, color = Color.White)

            Divider(color = Color.Gray, thickness = 1.dp)

            LazyColumn {
                items(bill.items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.name, color = Color.White)
                        Text("Qty: ${item.quantity}", color = Color.White)
                        Text("₹${item.price}", color = Color.Green)
                    }
                    Divider(color = Color.DarkGray, thickness = 0.5.dp)
                }
            }
        }
    }
}