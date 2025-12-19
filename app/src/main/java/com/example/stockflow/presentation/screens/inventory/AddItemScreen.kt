package com.example.stockflow.presentation.screens.inventory

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.CustomTextField
import com.example.stockflow.presentation.components.DatePickerField
import com.example.stockflow.presentation.components.DropdownTextField
import com.example.stockflow.presentation.components.FAB
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.screens.user.ErrorScreen
import com.example.stockflow.presentation.screens.user.LoadingScreen
import com.example.stockflow.presentation.viewmodel.CategoryViewModel
import com.example.stockflow.presentation.viewmodel.InventoryViewModel
import com.example.stockflow.presentation.viewmodel.SellingUnitViewModel
import com.example.stockflow.utils.safeNavigateOnce
import com.example.stockflow.utils.safePopBackStack
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddItemScreen(
    navController: NavController,
    viewModel: InventoryViewModel,
    sellingUnitViewModel: SellingUnitViewModel,
    categoryViewModel: CategoryViewModel
) {

    Log.d("Screen","Add Inventory Screen")

    val getCategoriesState by categoryViewModel.getCategoriesState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

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
    var expireDate by remember { mutableStateOf<LocalDate?>(null) }

    val sellingUnitState by sellingUnitViewModel.sellingUnitsState.collectAsState()
    val addInventoryState by viewModel.addInventoryState.collectAsState()

    LaunchedEffect(sellingUnitState) {
        if (sellingUnitState is UiState.Idle) {
            Log.d("Add Item Screen", "Selling Unit Screen Launched Effect")
            sellingUnitViewModel.getAllSellingUnits()
        }
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            sellingUnitViewModel.getAllSellingUnits()
            isRefreshing = false
        }
    }

    LaunchedEffect(addInventoryState) {
        if (addInventoryState is UiState.Success) {
            viewModel.resetGetAllInventoriesState()
            viewModel.resetAddItemState()
            navController.safePopBackStack()
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { isRefreshing = true }
    ) {
        when(addInventoryState){
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Success -> {

            }
            else -> {}
        }
        when (sellingUnitState) {
            is UiState.Success -> {

                val sellingUnit =
                    ((sellingUnitState as? UiState.Success)?.data?.data ?: emptyList())
                val sellingUnits = remember(sellingUnit) {
                    mutableStateListOf("Add New") + sellingUnit.map { it.unitName }
                }

                when (getCategoriesState){
                    is UiState.Success -> {
                        val categoryOptions = (getCategoriesState as UiState.Success).data.data?.filter { it.type == "INVENTORY" }?.map { it.name } ?: emptyList()
                        AppScaffold(
                            contentAlignment = Alignment.TopStart,
                            topBar = {
                                TopBar(
                                    title = "Add Item",
                                    navigationIcon = Icons.Outlined.ArrowBackIosNew,
                                    onNavigationClick = { navController.safePopBackStack() },
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
                                            .border(
                                                1.dp,
                                                Color.Gray,
                                                shape = RoundedCornerShape(8.dp)
                                            ),
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
                                    CustomTextField(
                                        label = "Name",
                                        value = name
                                    ) {
                                        name = it
                                    }
                                }

                                item {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        CustomTextField(
                                            label = "Sell Price",
                                            value = sellPrice,
                                            keyboardType = KeyboardType.Number,
                                            modifier = Modifier.weight(0.8f)
                                        ) {
                                            sellPrice = it
                                        }
                                        DropdownTextField(
                                            label = "Sell Price Unit",
                                            options = sellingUnits,
                                            selectedOption = sellPriceUnit,
                                            onOptionSelected = {
                                                if (it == "Add New") {
                                                    navController.safeNavigateOnce(Screens.AddSellingUnit.route)
                                                } else {
                                                    sellPriceUnit = it
                                                }
                                            },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }

                                item {
                                    DropdownTextField(
                                        label = "Category",
                                        options = categoryOptions,
                                        selectedOption = itemCategory,
                                        onOptionSelected = { itemCategory = it },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                item {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        CustomTextField(
                                            label = "MRP",
                                            value = mrp,
                                            keyboardType = KeyboardType.Number,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            mrp = it
                                        }
                                        CustomTextField(
                                            label = "Purchase Price",
                                            value = purchasePrice,
                                            keyboardType = KeyboardType.Number,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            purchasePrice = it
                                        }
                                    }
                                }

                                item {
                                    CustomTextField(
                                        label = "Quantity",
                                        value = quantity,
                                        keyboardType = KeyboardType.Number
                                    ) {
                                        quantity = it
                                    }
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
                                        CustomTextField(
                                            label = "Item Code",
                                            value = itemCode,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            itemCode = it
                                        }
                                        CustomTextField(
                                            label = "Barcode",
                                            value = barcode,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            barcode = it
                                        }
                                    }
                                }

                                item {
                                    CustomTextField(
                                        label = "Description",
                                        value = itemDescription
                                    ) {
                                        itemDescription = it
                                    }
                                }

                                item {
                                    CustomTextField(
                                        label = "Low Stock Alert",
                                        value = lowStockAlert,
                                        keyboardType = KeyboardType.Number
                                    ) {
                                        lowStockAlert = it
                                    }
                                }

                                item {
                                    CustomTextField(
                                        label = "Storage Location",
                                        value = storageLocation
                                    ) {
                                        storageLocation = it
                                    }
                                }

                                item {
                                    DatePickerField(
                                        label = "Expiry Date",
                                        selectedDate = remember { mutableStateOf(expireDate) },
                                        onDateSelected = { selected ->
                                            expireDate = selected
                                        }
                                    )
                                }
                            }
                        }
                    }
                    is UiState.Failed -> {
                        ErrorScreen()
                    }
                    is UiState.Idle -> {
                        categoryViewModel.getAllCategoriesByType("Inventory")
                    }
                    is UiState.Loading -> {
                        LoadingScreen()
                    }
                }
            }

            is UiState.Failed -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = (sellingUnitState as? UiState.Failed)?.message ?: "Something went wrong!", color = Color.Red)
                }
            }

            is UiState.Loading -> {
                LoadingScreen()
            }

            is UiState.Idle -> {}
        }
    }
}