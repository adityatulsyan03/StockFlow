package com.example.stockflow.presentation.screens

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.data.model.Party
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.DropdownTextField
import com.example.stockflow.presentation.components.FAB
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.viewmodel.PartyViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddPartyScreen(
    navController: NavController,
    viewModel: PartyViewModel
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var billingAddress by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }
    var deliveryPostalCode by remember { mutableStateOf("") }
    var gstNumber by remember { mutableStateOf("") }
    var isCustomer by remember { mutableStateOf(true) }
    var dob by remember { mutableStateOf<LocalDate?>(null) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            dob = LocalDate.of(year, month + 1, day)
        },
        LocalDate.now().year,
        LocalDate.now().monthValue - 1,
        LocalDate.now().dayOfMonth
    )

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Add Party",
                navigationIcon = Icons.Outlined.ArrowBackIosNew,
                onNavigationClick = { navController.popBackStack() },
                navigationIconContentDescription = "Back"
            )
        },
        floatingActionButton = {
            FAB(
                onClick = {
                    viewModel.createParty(
                        Party(
                            whatsappAlerts = true,
                            name = name,
                            phone = phone,
                            category = category,
                            address = billingAddress,
                            deliveryAddress = deliveryAddress,
                            deliveryPostalCode = deliveryPostalCode.toIntOrNull() ?: 0,
                            gstNumber = gstNumber,
                            customerSupplier = isCustomer,
                            dob = dob?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: ""
                        )
                    )
                },
                text = "ADD",
                extended = true,
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                DropdownTextField(
                    label = "Category",
                    options = listOf("Technology", "Wholesaler", "Distributor", "Manufacturer"),
                    selectedOption = category,
                    onOptionSelected = { category = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = billingAddress,
                        onValueChange = { billingAddress = it },
                        label = { Text("Billing Address") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = deliveryAddress,
                        onValueChange = { deliveryAddress = it },
                        label = { Text("Delivery Address") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                OutlinedTextField(
                    value = deliveryPostalCode,
                    onValueChange = { deliveryPostalCode = it },
                    label = { Text("Postal Code") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = gstNumber,
                    onValueChange = { gstNumber = it },
                    label = { Text("GST Number") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Customer/Supplier")
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = isCustomer,
                        onCheckedChange = { isCustomer = it }
                    )
                }
            }

            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = dob?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: "",
                        onValueChange = {},
                        label = { Text("Date of Birth") },
                        readOnly = true,
                        enabled = false,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            disabledLabelColor = Color.Gray,
                            disabledBorderColor = Color.Gray,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Pick Date",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(24.dp)
                            .clickable { datePickerDialog.show() }
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        }
    }
}
