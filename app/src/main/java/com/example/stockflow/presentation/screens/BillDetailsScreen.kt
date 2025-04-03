package com.example.stockflow.presentation.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bills
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.User
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.viewmodel.BillsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BillDetailsScreen(
    navController: NavController,
    viewModel: BillsViewModel,
    bill: Bills
) {
    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Bill Details",
                navigationIcon = Icons.Default.ArrowBackIosNew,
                navigationIconContentDescription = "Back",
                onNavigationClick = {
                    navController.popBackStack()
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