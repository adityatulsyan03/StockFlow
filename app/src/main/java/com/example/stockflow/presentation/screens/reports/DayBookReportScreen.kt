package com.example.stockflow.presentation.screens.reports

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.DayBookReport
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
fun DataTableScreen(
    navController: NavController,
    viewModel: ReportViewModel
) {

    var startDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    var endDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    var dateRange by remember { mutableStateOf("") }

    val dayBookReportState by viewModel.daybookReportState.collectAsState()

    LaunchedEffect(startDate, endDate) {
        viewModel.getDaybookReport(
            startDate = startDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: "",
            endDate = endDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: "",
        )
    }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Day Book Report",
                navigationIcon = Icons.Default.ArrowBackIosNew,
                navigationIconContentDescription = "Back",
                onNavigationClick = {
                    navController.safePopBackStack()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
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
                    onDateSelected = { newDate ->
                        startDate = newDate
                    }
                )
                Spacer(Modifier.width(16.dp))
                DatePickerField(
                    label = "End Date",
                    selectedDate = remember { mutableStateOf(endDate) },
                    modifier = Modifier.weight(1f),
                    onDateSelected = { newDate ->
                        endDate = newDate
                    }
                )
            }
            when (dayBookReportState) {

                is UiState.Idle -> {
                    viewModel.getMoneyReport(
                        startDate = startDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            ?: "",
                        endDate = endDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: "",
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
                    val dayBookReport = (dayBookReportState as UiState.Success).data.data
                    DayBookReportList(dayBookReports = dayBookReport)
                }

                is UiState.Failed -> {
                    val errorMessage = (dayBookReportState as UiState.Failed).message
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
fun DayBookReportList(dayBookReports: DayBookReport?) {

    Log.d("Screen","Day Book Report Screen")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Field", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        Text("Value", style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }

    Divider(color = Color.Gray, thickness = 1.dp)

    val reports = listOf(
        "Money In" to dayBookReports?.moneyIn,
        "Cash In" to dayBookReports?.moneyInCash,
        "Cheque In" to dayBookReports?.moneyInCheque,
        "UPI In" to dayBookReports?.moneyInUPI,
        "Money Out" to dayBookReports?.moneyOut,
        "Cash Out" to dayBookReports?.moneyOutCash,
        "Cheque Out" to dayBookReports?.moneyOutCheque,
        "UPI Out" to dayBookReports?.moneyOutUPI
    )

    LazyColumn {
        items(reports){ (field, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(field, color = Color.LightGray)
                Text(value.toString(), color = Color.White)
            }
            Divider(color = Color.DarkGray, thickness = 0.5.dp)
        }
    }
}