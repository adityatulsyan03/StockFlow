package com.example.stockflow.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.presentation.navigation.Screens

@Composable
fun SidebarDrawer(
    navController: NavController,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier
            .width(250.dp) // Set the width to half or any fixed size you prefer
            .fillMaxHeight() // Make it take full height
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Make it scrollable if content overflows
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Reports",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SidebarItem("Bills", Icons.Default.Checklist) {
                navController.navigate(Screens.BillScreen.route)
                onCloseDrawer()
            }

            SidebarItem("Day Book Report", Icons.Default.ReceiptLong) {
                navController.navigate(Screens.DayBookReportScreen.route)
                onCloseDrawer()
            }

            SidebarItem("Money Report", Icons.Default.Money) {
                navController.navigate(Screens.MoneyReportScreen.route)
                onCloseDrawer()
            }

            SidebarItem("Stock Report", Icons.Default.Storage) {
                navController.navigate(Screens.StockReportScreen.route)
                onCloseDrawer()
            }

            SidebarItem("Transaction Report", Icons.Default.BarChart) {
                navController.navigate(Screens.TransactionReportScreen.route)
                onCloseDrawer()
            }
        }
    }
}

@Composable
fun SidebarItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
    }
}