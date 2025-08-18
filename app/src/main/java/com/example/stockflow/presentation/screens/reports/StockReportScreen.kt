package com.example.stockflow.presentation.screens.reports

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.example.stockflow.data.model.StockReport
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.viewmodel.ReportViewModel
import com.example.stockflow.utils.safePopBackStack

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockReportScreen(
    navController: NavController,
    viewModel: ReportViewModel
) {
    Log.d("Screen","Stock Report Screen")

    val stockReportState by viewModel.stockReportState.collectAsState()

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Stock Report",
                navigationIcon = Icons.Default.ArrowBackIosNew,
                navigationIconContentDescription = "Back",
                onNavigationClick = {
                    navController.safePopBackStack()
                }
            )
        }
    ) {
        when (stockReportState) {

            is UiState.Idle -> {
                viewModel.getStockReport()
            }

            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp),
                    color = Color.White
                )
            }

            is UiState.Success -> {

                val stockReports = (stockReportState as UiState.Success).data.data ?: emptyList()

                if (stockReports.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No items found", color = Color.Gray)
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        StockReportTable(stockReports = stockReports)
                    }
                }
            }

            is UiState.Failed -> {
                val errorMessage = (stockReportState as UiState.Failed).message
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

@Composable
fun StockReportTable(stockReports: List<StockReport>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Item", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        Text("Purchased", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        Text("Sold", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        Text("Stock Left", style = MaterialTheme.typography.bodyLarge, color = Color.White)
//        Text("Price", style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }

    Divider(color = Color.Gray, thickness = 1.dp)

    LazyColumn {
        items(stockReports) { stock ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stock.itemName, color = Color.LightGray)
                Text(stock.purchased, color = Color.White)
                Text(stock.sold, color = Color.Red)
                Text(stock.leftStock, color = Color.Green)
//                Text(stock.price, color = Color.White)
            }
            Divider(color = Color.DarkGray, thickness = 0.5.dp)
        }
    }
}