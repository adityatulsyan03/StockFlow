package com.example.stockflow.presentation.screens

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.DatePickerField
import com.example.stockflow.presentation.components.DropdownTextField
import com.example.stockflow.presentation.components.TopBar
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataTableScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val startDate by remember { mutableStateOf<LocalDate?>(null) }
    val endDate by remember { mutableStateOf<LocalDate?>(null) }
    var dateRange by remember { mutableStateOf("") }

    val tableData = listOf(
        "ID" to "1",
        "Date" to "2025-03-20",
        "Money In" to "5000",
        "Cash In" to "2000",
        "Cheque In" to "1000",
        "UPI In" to "2000",
        "Money Out" to "3000",
        "Cash Out" to "1500",
        "Cheque Out" to "500",
        "UPI Out" to "1000"
    )

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Transaction Summary",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DropdownTextField(
                label = "Date Range",
                options = listOf("Today","Last Week", "Last Month", "Last Year"),
                selectedOption = dateRange,
                onOptionSelected = { dateRange = it },
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
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(16.dp))
                DatePickerField(
                    label = "End Date",
                    selectedDate = remember { mutableStateOf(endDate) },
                    modifier = Modifier.weight(1f)
                )
            }

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

            LazyColumn {
                items(tableData) { (field, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(field, color = Color.LightGray)
                        Text(value, color = Color.White)
                    }
                    Divider(color = Color.DarkGray, thickness = 0.5.dp)
                }
            }
        }
    }
}