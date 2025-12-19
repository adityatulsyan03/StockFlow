package com.example.stockflow.presentation.screens.reports

import android.os.Build
import android.util.Log
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.BillItem
import com.example.stockflow.data.model.Bills
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.data.model.Party
import com.example.stockflow.presentation.components.*
import com.example.stockflow.presentation.screens.user.LoadingScreen
import com.example.stockflow.presentation.viewmodel.BillsViewModel
import com.example.stockflow.presentation.viewmodel.InventoryViewModel
import com.example.stockflow.presentation.viewmodel.PartyViewModel
import com.example.stockflow.presentation.viewmodel.ReportViewModel
import com.example.stockflow.utils.safePopBackStack
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBillScreen(
    navController: NavController,
    viewModel: BillsViewModel,
    partyViewModel: PartyViewModel,
    inventoryViewModel: InventoryViewModel,
    reportViewModel: ReportViewModel
) {
    Log.d("Screen","Add Bill Screen")

    val partyState = partyViewModel.getAllPartiesState.collectAsState().value
    val inventoryState = inventoryViewModel.getAllInventoriesState.collectAsState().value

    when {
        partyState is UiState.Idle -> {
            partyViewModel.getAllParties()
        }

        inventoryState is UiState.Idle -> {
            inventoryViewModel.getAllInventories()
        }

        partyState is UiState.Loading || inventoryState is UiState.Loading -> {
            LoadingScreen()
        }

        partyState is UiState.Failed || inventoryState is UiState.Failed -> {
            val errorMessage = (partyState as? UiState.Failed)?.message
                ?: (inventoryState as? UiState.Failed)?.message
                ?: "Something went wrong!"

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = errorMessage, color = Color.Red)
            }
        }

        partyState is UiState.Success && inventoryState is UiState.Success -> {
            val parties = partyState.data.data ?: emptyList()
            val inventories = inventoryState.data.data ?: emptyList()

            AddBillScreenUI(navController, viewModel, parties, inventories,reportViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBillScreenUI(
    navController: NavController,
    viewModel: BillsViewModel,
    parties: List<Party>,
    inventories: List<Inventory>,
    reportViewModel: ReportViewModel
) {

    val addBillState by viewModel.addBillState.collectAsState()

    LaunchedEffect(addBillState) {
        if (addBillState is UiState.Success) {
            Log.d("Add Bill","UiState Success")
            viewModel.resetAddBillState()
            viewModel.resetGetBillState()
            reportViewModel.resetState()
            navController.safePopBackStack()
        }
    }

    when (addBillState){
        is UiState.Loading -> {
            LoadingScreen()
        }
        else -> {}
    }

    var partyName by remember { mutableStateOf("") }
    val billDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val billTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    var partyId by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("CASH") }
    var totalAmount by remember { mutableStateOf(0.0f) }
    var items by remember { mutableStateOf(listOf<BillItem>()) }
    var isInvoice by remember { mutableStateOf(false) }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Add Bill",
                navigationIcon = Icons.Outlined.ArrowBackIosNew,
                onNavigationClick = { navController.safePopBackStack() },
                navigationIconContentDescription = "Back"
            )
        },
        floatingActionButton = {
            FAB(
                onClick = {
                    viewModel.addBill(
                        Bills(
                            billDate = billDate,
                            billTime = billTime,
                            items = items,
                            partyId = partyId,
                            paymentMethod = paymentMethod,
                            totalAmount = totalAmount,
                            partyName = partyName,
                            isInvoice = isInvoice
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
                var isDropdownExpanded by remember { mutableStateOf(false) }

                val filteredItems = parties.filter { it.name.contains(partyName, ignoreCase = true) }
                CustomTextField(
                    value = partyName,
                    label = "Party Name"
                ) {
                    partyName = it
                    isDropdownExpanded = it.isNotEmpty()
                }
                if (isDropdownExpanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.Gray)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp)
                                .padding(4.dp)
                        ) {
                            if (filteredItems.isNotEmpty()) {
                                items(filteredItems.size) { index ->
                                    val partyItem = filteredItems[index]
                                    Text(
                                        text = partyItem.name,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                partyName = partyItem.name
                                                isDropdownExpanded = false
                                                partyId = partyItem.id?:""
                                                isInvoice = partyItem.customerSupplier
                                            }
                                            .padding(8.dp),
                                        color = Color.White
                                    )
                                    Divider(color = Color.Gray, thickness = 0.5.dp)
                                }
                            } else {
                                item {
                                    Text(
                                        text = "No party",
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
            }

            item {
                DropdownTextField(
                    label = "Payment Method",
                    options = listOf("CASH", "CHEQUE", "UPI"),
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
                    onUpdate = { newName, newQuantity->
                        items = items.toMutableList().apply {
                            val t = (inventories.find { it.name == newName }?.sellPrice ?: 0.0f) * (newQuantity.toIntOrNull() ?: 0)
                            this[index] = this[index].copy(
                                id = inventories.find { it.name == newName }?.id ?: "",
                                name = newName,
                                quantity = newQuantity.toIntOrNull() ?: 0,
                                price = inventories.find { it.name == newName }?.sellPrice ?: 0.0f,
                                totalPrice = t + t*(inventories.find { it.name == newName }?.tax ?: 0.0f)
                            )
                        }
                        totalAmount += items[index].totalPrice
                    },
                    onRemove = {
                        totalAmount -= items[index].totalPrice
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
                            quantity = 0,
                            price = 0.0f,
                            totalPrice = 0.0f
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
    inventories: List<Inventory>
) {
    var name by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity.toString()) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val filteredItems = inventories.filter { it.name.contains(name, ignoreCase = true) }

    Row(
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
                    ){
                        if (filteredItems.isNotEmpty()) {
                            items(filteredItems.size) { index ->
                                val inventoryItem = filteredItems[index]
                                Text(
                                    text = inventoryItem.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            name = inventoryItem.name
                                            quantity = "0" // Default quantity
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

