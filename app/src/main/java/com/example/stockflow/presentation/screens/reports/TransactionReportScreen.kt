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
import com.example.stockflow.data.model.TransactionReport
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.DatePickerField
import com.example.stockflow.presentation.components.DropdownTextField
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.viewmodel.ReportViewModel
import com.example.stockflow.utils.safePopBackStack
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionReportScreen(
    navController: NavController,
    viewModel: ReportViewModel
) {
    Log.d("Screen","Transaction Report Screen")

    var startDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    var endDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    var dateRange by remember { mutableStateOf("") }

    val transactionReportState by viewModel.transactionsReportState.collectAsState()

    LaunchedEffect(startDate, endDate) {
        viewModel.getTransactionsReport(
            startDate = startDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: "",
            endDate = endDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: ""
        )
    }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Transaction Report",
                navigationIcon = Icons.Default.ArrowBackIosNew,
                navigationIconContentDescription = "Back",
                onNavigationClick = { navController.safePopBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DropdownTextField(
                label = "Date Range",
                options = listOf("Today", "Last Week", "Last Month", "Last Year"),
                selectedOption = dateRange,
                onOptionSelected = {
                    dateRange = it
                    when (it) {
                        "Today" -> {
                            val today = LocalDate.now()
                            startDate = today
                            endDate = today
                        }
                        "Last Week" -> {
                            val today = LocalDate.now()
                            startDate = today.minusWeeks(1)
                            endDate = today
                        }
                        "Last Month" -> {
                            val today = LocalDate.now()
                            startDate = today.minusMonths(1)
                            endDate = today
                        }
                        "Last Year" -> {
                            val today = LocalDate.now()
                            startDate = today.minusYears(1)
                            endDate = today
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DatePickerField(
                    label = "Start Date",
                    selectedDate = remember { mutableStateOf(startDate) },
                    modifier = Modifier.weight(1f),
                    onDateSelected = { date ->
                        startDate = date
                    }
                )
                Spacer(Modifier.width(16.dp))
                DatePickerField(
                    label = "End Date",
                    selectedDate = remember { mutableStateOf(endDate) },
                    modifier = Modifier.weight(1f),
                    onDateSelected = { date ->
                        endDate = date
                    }
                )
            }

            when (transactionReportState) {
                is UiState.Idle -> {
                    viewModel.getTransactionsReport(
                        startDate = startDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: "",
                        endDate = endDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: ""
                    )
                }

                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp),
                            color = Color.White
                        )
                    }
                }

                is UiState.Success -> {
                    val transactionReports = (transactionReportState as UiState.Success).data.data ?: emptyList()
                    if (transactionReports.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No items found", color = Color.Gray)
                        }
                    } else {
                        TransactionReportList(transactionData = transactionReports)
                    }
                }

                is UiState.Failed -> {
                    val errorMessage = (transactionReportState as UiState.Failed).message
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
fun TransactionReportList(transactionData: List<TransactionReport>) {
    // Table Header
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Date", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        Text("Bill No", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        Text("Party", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        Text("Amount", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        Text("Mode", style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }

    Divider(color = Color.Gray, thickness = 1.dp)

    LazyColumn {
        items(transactionData) { transaction ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(transaction.date, color = Color.LightGray)
//                Text(transaction.billNo, color = if (transaction.isSales) Color.Green else Color.Red)
//                Text(transaction.partyName, color = Color.White)
                Text(transaction.totalAmount, color = Color.White)
                Text(transaction.paymentMethod, color = Color.White)
            }
            Divider(color = Color.DarkGray, thickness = 0.5.dp)
        }
    }
}