package com.example.stockflow.presentation.screens.party

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Party
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.CustomTextField
import com.example.stockflow.presentation.components.DatePickerField
import com.example.stockflow.presentation.components.DropdownTextField
import com.example.stockflow.presentation.components.FAB
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.screens.user.ErrorScreen
import com.example.stockflow.presentation.screens.user.LoadingScreen
import com.example.stockflow.presentation.viewmodel.CategoryViewModel
import com.example.stockflow.presentation.viewmodel.PartyViewModel
import com.example.stockflow.utils.safePopBackStack
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditPartyScreen(
    party: Party,
    navController: NavHostController,
    viewModel: PartyViewModel,
    categoryViewModel: CategoryViewModel
) {

    Log.d("Screen", "Edit Party Screen")

    val updatePartyState by viewModel.updatePartyState.collectAsState()
    val getCategoriesState by categoryViewModel.getCategoriesState.collectAsState()

    when(updatePartyState){
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Success -> {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("refreshParty", true)
            navController.safePopBackStack()
            viewModel.resetUpdatePartyState()
        }
        else -> {}
    }

    LaunchedEffect(getCategoriesState) {
        if (getCategoriesState is UiState.Idle) {
            categoryViewModel.getAllCategoriesByType("Party")
        }
    }

    var name by remember { mutableStateOf(party.name) }
    var phone by remember { mutableStateOf(party.phone) }
    var category by remember { mutableStateOf(party.category) }
    var billingAddress by remember { mutableStateOf(party.address) }
    var deliveryAddress by remember { mutableStateOf(party.deliveryAddress) }
    var deliveryPostalCode by remember { mutableStateOf(party.deliveryPostalCode.toString()) }
    var gstNumber by remember { mutableStateOf(party.gstNumber) }
    var isCustomer by remember { mutableStateOf(party.customerSupplier) }
    var dob by remember { mutableStateOf(
        party.dob.takeIf { it.isNotEmpty() }?.let { LocalDate.parse(it) }
    ) }

    when (getCategoriesState) {
        is UiState.Success -> {
            val categoryOptions = (getCategoriesState as UiState.Success).data.data?.map { it.name } ?: emptyList()

            AppScaffold(
                contentAlignment = Alignment.TopStart,
                topBar = {
                    TopBar(
                        title = "Edit Party",
                        navigationIcon = Icons.Outlined.ArrowBackIosNew,
                        onNavigationClick = { navController.safePopBackStack() },
                        navigationIconContentDescription = "Back"
                    )
                },
                floatingActionButton = {
                    FAB(
                        onClick = {
                            viewModel.updatePartyById(
                                partyId = party.id?:"",
                                party = party.copy(
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
                        text = "UPDATE",
                        extended = true
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    item {
                        CustomTextField(
                            label = "Name",
                            value = name
                        ) {
                            name = it
                        }
                    }

                    item {
                        CustomTextField(
                            label = "Phone",
                            value = phone,
                            keyboardType = KeyboardType.Phone
                        ) {
                            phone = it
                        }
                    }

                    item {
                        DropdownTextField(
                            label = "Category",
                            options = categoryOptions,
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
                            CustomTextField(
                                label = "Billing Address",
                                value = billingAddress,
                                modifier = Modifier.weight(1f)
                            ) {
                                billingAddress = it
                            }
                            CustomTextField(
                                label = "Delivery Address",
                                value = deliveryAddress,
                                modifier = Modifier.weight(1f)
                            ) {
                                deliveryAddress = it
                            }
                        }
                    }

                    item {
                        CustomTextField(
                            label = "Postal Code",
                            value = deliveryPostalCode,
                            keyboardType = KeyboardType.Number
                        ) {
                            deliveryPostalCode = it
                        }
                    }

                    item {
                        CustomTextField(
                            label = "GST Number",
                            value = gstNumber
                        ) {
                            gstNumber = it
                        }
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
                        DatePickerField(
                            label = "Date of Birth",
                            selectedDate = remember { mutableStateOf(dob) },
                            onDateSelected = { selected ->
                                dob = selected
                            }
                        )
                    }
                }
            }
        }

        is UiState.Failed -> ErrorScreen()
        is UiState.Loading -> LoadingScreen()
        is UiState.Idle -> {}
    }

}