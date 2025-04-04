package com.example.stockflow.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.DatePickerField
import com.example.stockflow.presentation.components.DropdownTextField
import com.example.stockflow.presentation.components.FAB
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.viewmodel.InventoryViewModel
import com.example.stockflow.presentation.viewmodel.SellingUnitViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddItemScreen(
    navController: NavController,
    viewModel: InventoryViewModel,
    sellingUnitViewModel: SellingUnitViewModel
) {

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            sellingUnitViewModel.getAllSellingUnits()
            isRefreshing = false
        }
    }

    val sellingUnitState by sellingUnitViewModel.sellingUnitsState.collectAsState()

    LaunchedEffect(Unit) {
        if (sellingUnitState is UiState.Idle) {
            sellingUnitViewModel.getAllSellingUnits()
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { isRefreshing = true } // Trigger refresh
    ) {
        when (sellingUnitState) {
            is UiState.Success -> {

                val sellingUnit =
                    ((sellingUnitState as? UiState.Success)?.data?.data ?: emptyList())
                val sellingUnits = remember(sellingUnit) {
                    mutableStateListOf("Add New") + sellingUnit.map { it.unitName }
                }

                val photo by remember { mutableStateOf("") }
                var name by remember { mutableStateOf("") }
                var quantity by remember { mutableStateOf("") }
                var sellPrice by remember { mutableStateOf("") }
                var sellPriceUnit by remember { mutableStateOf("") }
                var itemCategory by remember { mutableStateOf("") }
                var mrp by remember { mutableStateOf("") }
                var purchasePrice by remember { mutableStateOf("") }
                var tax by remember { mutableStateOf("") }
                var itemCode by remember { mutableStateOf("") }
                var barcode by remember { mutableStateOf("") }
                var itemDescription by remember { mutableStateOf("") }
                var lowStockAlert by remember { mutableStateOf("") }
                var storageLocation by remember { mutableStateOf("") }
                val expireDate by remember { mutableStateOf<LocalDate?>(null) }

                AppScaffold(
                    contentAlignment = Alignment.TopStart,
                    topBar = {
                        TopBar(
                            title = "Add Item",
                            navigationIcon = Icons.Outlined.ArrowBackIosNew,
                            onNavigationClick = { navController.popBackStack() },
                            navigationIconContentDescription = "Back"
                        )
                    },
                    floatingActionButton = {
                        FAB(
                            onClick = {
                                viewModel.addInventory(
                                    Inventory(
                                        photo = photo,
                                        name = name,
                                        quantity = quantity.toIntOrNull() ?: 0,
                                        sellPrice = sellPrice.toFloatOrNull() ?: 0.0f,
                                        sellPriceUnit = sellPriceUnit,
                                        itemCategory = itemCategory,
                                        mrp = mrp.toFloatOrNull() ?: 0.0f,
                                        purchasePrice = purchasePrice.toFloatOrNull() ?: 0.0f,
                                        tax = tax.toFloatOrNull() ?: 0.0f,
                                        itemCode = itemCode,
                                        barcode = barcode,
                                        itemDescription = itemDescription,
                                        lowStockAlert = lowStockAlert.toIntOrNull() ?: 0,
                                        storageLocation = storageLocation,
                                        expireDate = expireDate?.format(
                                            DateTimeFormatter.ofPattern(
                                                "yyyy-MM-dd"
                                            )
                                        ) ?: ""
                                    )
                                )
                            },
                            modifier = Modifier,
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
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Add Photo",
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Name") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedTextField(
                                    value = sellPrice,
                                    onValueChange = { sellPrice = it },
                                    label = { Text("Sell Price") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(0.8f)
                                )
                                DropdownTextField(
                                    label = "Sell Price Unit",
                                    options = sellingUnits,
                                    selectedOption = sellPriceUnit,
                                    onOptionSelected = {
                                        if (it == "Add New") {
                                            navController.navigate(Screens.AddSellingUnit.route)
                                        } else {
                                            sellPriceUnit = it
                                        }
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = itemCategory,
                                onValueChange = { itemCategory = it },
                                label = { Text("Category") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedTextField(
                                    value = mrp,
                                    onValueChange = { mrp = it },
                                    label = { Text("MRP") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = purchasePrice,
                                    onValueChange = { purchasePrice = it },
                                    label = { Text("Purchase Price") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = quantity,
                                onValueChange = { quantity = it },
                                label = { Text("Quantity") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item {
                            DropdownTextField(
                                label = "Tax",
                                options = listOf("0.0", "5.0", "12.0", "18.0", "28.0"),
                                selectedOption = tax,
                                onOptionSelected = { tax = it },
                                modifier = Modifier.fillMaxWidth()
                            )

                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedTextField(
                                    value = itemCode,
                                    onValueChange = { itemCode = it },
                                    label = { Text("Item Code") },
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = barcode,
                                    onValueChange = { barcode = it },
                                    label = { Text("Barcode") },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = itemDescription,
                                onValueChange = { itemDescription = it },
                                label = { Text("Description") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = lowStockAlert,
                                onValueChange = { lowStockAlert = it },
                                label = { Text("Low Stock Alert") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = storageLocation,
                                onValueChange = { storageLocation = it },
                                label = { Text("Storage Location") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item {
                            DatePickerField(
                                label = "Expiry Date",
                                selectedDate = remember { mutableStateOf(expireDate) },
                                onDateSelected = { }
                            )
                        }
                    }
                }
            }

            is UiState.Failed -> {
                val errorMessage =
                    (sellingUnitState as? UiState.Failed)?.message ?: "Something went wrong!"
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = errorMessage, color = Color.Red)
                }
            }

            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Idle -> {}
        }
    }
}