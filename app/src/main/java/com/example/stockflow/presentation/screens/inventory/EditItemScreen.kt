package com.example.stockflow.presentation.screens.inventory

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.presentation.components.*
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.screens.user.LoadingScreen
import com.example.stockflow.presentation.viewmodel.InventoryViewModel
import com.example.stockflow.presentation.viewmodel.SellingUnitViewModel
import com.example.stockflow.utils.safePopBackStack
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditItemScreen(
    item: Inventory,
    navController: NavHostController,
    inventoryViewModel: InventoryViewModel,
    sellingUnitViewModel: SellingUnitViewModel
) {

    Log.d("Screen","Edit Inventory Screen")

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val initialExpireDate = remember(item.expireDate) {
        item.expireDate?.let { LocalDate.parse(it, formatter) }
    }

    val updateItemState by inventoryViewModel.updateInventoryState.collectAsState()
    val sellingUnitState by sellingUnitViewModel.sellingUnitsState.collectAsState()

    LaunchedEffect(updateItemState) {
        if (updateItemState is UiState.Success) {
            inventoryViewModel.resetGetAllInventoriesState()
            inventoryViewModel.resetUpdateItemState()
            navController.safePopBackStack()
        }
    }

    when(updateItemState){
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Success -> {
        }
        else -> {}
    }

    LaunchedEffect(sellingUnitState) {
        if (sellingUnitState is UiState.Idle) {
            sellingUnitViewModel.getAllSellingUnits()
        }
    }

    var photo by remember { mutableStateOf(item.photo?:"") }
    var name by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity.toString()) }
    var sellPrice by remember { mutableStateOf(item.sellPrice.toString()) }
    var sellPriceUnit by remember { mutableStateOf(item.sellPriceUnit) }
    var itemCategory by remember { mutableStateOf(item.itemCategory) }
    var mrp by remember { mutableStateOf(item.mrp.toString()) }
    var purchasePrice by remember { mutableStateOf(item.purchasePrice.toString()) }
    var tax by remember { mutableStateOf(item.tax?.toString() ?: "0.0") }
    var itemCode by remember { mutableStateOf(item.itemCode.orEmpty()) }
    var barcode by remember { mutableStateOf(item.barcode.orEmpty()) }
    var itemDescription by remember { mutableStateOf(item.itemDescription.orEmpty()) }
    var lowStockAlert by remember { mutableStateOf(item.lowStockAlert?.toString() ?: "0") }
    var storageLocation by remember { mutableStateOf(item.storageLocation.orEmpty()) }
    val expireDate = remember { mutableStateOf(initialExpireDate) }

    when (sellingUnitState) {
        is UiState.Success -> {

            val sellingUnit =
                ((sellingUnitState as? UiState.Success)?.data?.data ?: emptyList())
            val sellingUnits = remember(sellingUnit) {
                mutableStateListOf("Add New") + sellingUnit.map { it.unitName }
            }

            AppScaffold(
                contentAlignment = Alignment.TopStart,
                topBar = {
                    TopBar(
                        title = "Edit Item",
                        navigationIcon = Icons.Outlined.ArrowBackIosNew,
                        onNavigationClick = { navController.safePopBackStack() },
                        navigationIconContentDescription = "Back"
                    )
                },
                floatingActionButton = {
                    FAB(
                        onClick = {
                            inventoryViewModel.updateInventoryById(
                                inventoryId = item.id ?: "",
                                inventory = item.copy(
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
                                    expireDate = expireDate.value?.format(
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                    ) ?: ""
                                )
                            )
                        },
                        modifier = Modifier,
                        text = "SAVE",
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
                                contentDescription = "Edit Photo",
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
                        OutlinedTextField(
                            value = sellPrice,
                            onValueChange = { sellPrice = it },
                            label = { Text("Sell Price") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
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
                            }
                        )
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
                        OutlinedTextField(
                            value = tax,
                            onValueChange = { tax = it },
                            label = { Text("Tax (%)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                            selectedDate = expireDate,
                            onDateSelected = { expireDate.value = it }
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
            LoadingScreen()
        }

        is UiState.Idle -> {}
    }
}