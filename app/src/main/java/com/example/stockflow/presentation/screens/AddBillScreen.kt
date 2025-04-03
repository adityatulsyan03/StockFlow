package com.example.stockflow.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.data.model.BillItem
import com.example.stockflow.data.model.Bills
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.presentation.components.*
import com.example.stockflow.presentation.viewmodel.BillsViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBillScreen(
    navController: NavController,
    viewModel: BillsViewModel
) {
    val context = LocalContext.current

    val parties by remember { mutableStateOf<List<Inventory>?>(emptyList()) }
//    val inventories by remember { mutableStateOf<List<Inventory>?>(emptyList()) }

    val inventories = listOf(
        Inventory(
            id = "1",
            photo = null,
            name = "Apple",
            quantity = 50,
            sellPrice = 120.0f,
            sellPriceUnit = "kg",
            itemCategory = "Fruits",
            mrp = 150.0f,
            purchasePrice = 100.0f,
            tax = 5.0,
            itemCode = "APL123",
            barcode = "1234567890123",
            itemDescription = "Fresh apples from Kashmir",
            lowStockAlert = 10,
            storageLocation = "Shelf A1",
            expireDate = "2025-12-31"
        ),
        Inventory(
            id = "2",
            photo = null,
            name = "Milk",
            quantity = 20,
            sellPrice = 50.0f,
            sellPriceUnit = "liter",
            itemCategory = "Dairy",
            mrp = 60.0f,
            purchasePrice = 45.0f,
            tax = 2.5,
            itemCode = "MLK456",
            barcode = "9876543210987",
            itemDescription = "Pure cow milk",
            lowStockAlert = 5,
            storageLocation = "Refrigerator",
            expireDate = "2024-06-15"
        ),
        Inventory(
            id = "3",
            photo = null,
            name = "Bread",
            quantity = 30,
            sellPrice = 35.0f,
            sellPriceUnit = "pack",
            itemCategory = "Bakery",
            mrp = 40.0f,
            purchasePrice = 30.0f,
            tax = 1.5,
            itemCode = "BRD789",
            barcode = "3216549870123",
            itemDescription = "Whole wheat bread",
            lowStockAlert = 8,
            storageLocation = "Shelf B2",
            expireDate = "2024-04-10"
        ),
        Inventory(
            id = "4",
            photo = null,
            name = "Rice",
            quantity = 100,
            sellPrice = 80.0f,
            sellPriceUnit = "kg",
            itemCategory = "Grains",
            mrp = 90.0f,
            purchasePrice = 70.0f,
            tax = 5.0,
            itemCode = "RCE321",
            barcode = "4561237890123",
            itemDescription = "Basmati rice",
            lowStockAlert = 20,
            storageLocation = "Warehouse Section C",
            expireDate = "2026-01-01"
        ),
        Inventory(
            id = "5",
            photo = null,
            name = "Eggs",
            quantity = 60,
            sellPrice = 6.0f,
            sellPriceUnit = "piece",
            itemCategory = "Poultry",
            mrp = 8.0f,
            purchasePrice = 5.0f,
            tax = 1.0,
            itemCode = "EGG654",
            barcode = "7894561230987",
            itemDescription = "Farm fresh eggs",
            lowStockAlert = 12,
            storageLocation = "Cold Storage",
            expireDate = "2024-05-20"
        )
    )

    val billDate by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))}
    val billTime by remember { mutableStateOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))}
    var partyName by remember { mutableStateOf("") }
    val partyId by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("Cash") }
    val totalAmount by remember { mutableStateOf("") }
    var items by remember { mutableStateOf(listOf<BillItem>()) }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Add Bill",
                navigationIcon = Icons.Outlined.ArrowBackIosNew,
                onNavigationClick = { navController.popBackStack() },
                navigationIconContentDescription = "Back"
            )
        },
        floatingActionButton = {
            FAB(
                onClick = {
                    viewModel.postBill(
                        Bills(
                            id = System.currentTimeMillis().toString(),
                            billDate = billDate,
                            billTime = billTime,
                            items = items,
                            partyId = partyId,
                            paymentMethod = paymentMethod,
                            totalAmount = totalAmount.toIntOrNull() ?: 0,
                            partyName = partyName,
                            isInvoice = true
                        )
                    )
                    Toast.makeText(context, "Bill Added", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
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
                    value = partyName,
                    onValueChange = { partyName = it },
                    label = { Text("Party Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                DropdownTextField(
                    label = "Payment Method",
                    options = listOf("Cash", "Cheque", "UPI"),
                    selectedOption = paymentMethod,
                    onOptionSelected = { paymentMethod = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Text(
                    text = "Bill Items",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(items.size) { index ->
                BillItemRow(
                    item = items[index],
                    onUpdate = { newName, newQuantity ->
                        items = items.toMutableList().apply {
                            this[index] = this[index].copy(name = newName, quantity = newQuantity)
                        }
                    },
                    onRemove = {
                        items = items.toMutableList().apply { removeAt(index) }
                    },
                    inventories = inventories
                )
            }

            item {
                Button(
                    onClick = {
                        items = items + BillItem(
                            name = "",
                            quantity = "",
                            price = 0
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Item")
                }
            }
        }
    }
}

@Composable
fun BillItemRow(
    item: BillItem,
    onUpdate: (String, String) -> Unit,
    onRemove: () -> Unit,
    inventories: List<Inventory>? // List of inventory items
) {
    var name by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val filteredItems = inventories?.filter { it.name.contains(name, ignoreCase = true) } ?: emptyList()

    Row( // ✅ Use Row to align elements horizontally
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) { // ✅ Column takes most of the space
            // Item Name Input
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    isDropdownExpanded = it.isNotEmpty() // Show dropdown when user types
                    onUpdate(it, quantity)
                },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Search List (Dropdown)
            if (isDropdownExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp) // ✅ Fixed height to avoid infinite constraints
                            .padding(4.dp)
                    ) {
                        if (filteredItems.isNotEmpty()) {
                            items(filteredItems.size) { index ->
                                val inventoryItem = filteredItems[index]
                                Text(
                                    text = inventoryItem.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            name = inventoryItem.name
                                            quantity = "1" // Default quantity
                                            onUpdate(inventoryItem.name, quantity)
                                            isDropdownExpanded = false // Hide dropdown
                                        }
                                        .padding(8.dp),
                                    color = Color.White
                                )
                                Divider(color = Color.Gray, thickness = 0.5.dp)
                            }
                        } else {
                            item {
                                Text(
                                    text = "No items",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    color = Color.Red
                                )
                            }
                        }
                    }
                }
            }

            // Quantity Input
            OutlinedTextField(
                value = quantity,
                onValueChange = {
                    quantity = it
                    onUpdate(name, it)
                },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // ✅ Delete Icon (Moved to Side)
        IconButton(
            onClick = onRemove,
            modifier = Modifier.padding(start = 8.dp) // Adds spacing from inputs
        ) {
            Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Remove Item")
        }
    }
}

