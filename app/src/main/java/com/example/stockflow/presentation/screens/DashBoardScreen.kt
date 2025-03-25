package com.example.stockflow.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.DensityMedium
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bank
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.BankDetails
import com.example.stockflow.presentation.components.BillsList
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.navigation.BottomNavBar
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.viewmodel.UserDetailViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun DashBoardScreen(
    navController: NavController,
    viewModel: UserDetailViewModel
) {
    val bankState = viewModel.bankState.collectAsState().value

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        topBar = {
            TopBar(
                title = "John Doe",
                navigationIcon = Icons.Outlined.DensityMedium,
                onNavigationClick = { navController.navigate(Screens.DayBookReportScreen.route) },
                navigationIconContentDescription = "Side Bar",
                onTitleClick = { navController.navigate(Screens.UserScreen.route) },
                trailingIcon = Icons.Outlined.Clear,
                onTrailingClick = {
                    Firebase.auth.signOut()
                    navController.navigate(Screens.LoginScreen.route)
                },
                trailingIconContentDescription = "Logout"
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (bankState) {

                    is UiState.Idle -> {
                        viewModel.getBankDetails()
                    }

                    is UiState.Loading -> {
                        Text(text = "Loading bank details...", fontSize = 16.sp)
                    }

                    is UiState.Success -> {
                        val bank = bankState.data.data

                        BankDetails(
                            bank = Bank(
                                id = bank?.id?.takeIf { it.isNotBlank() } ?: "N/A",
                                bankAccountNumber = bank?.bankAccountNumber?.takeIf { it.isNotBlank() } ?: "N/A",
                                bankName = bank?.bankName?.takeIf { it.isNotBlank() } ?: "N/A",
                                ifscCode = bank?.ifscCode?.takeIf { it.isNotBlank() } ?: "N/A",
                                accountHolderName = bank?.accountHolderName?.takeIf { it.isNotBlank() } ?: "N/A",
                                upiId = bank?.upiId?.takeIf { it.isNotBlank() } ?: "N/A"
                            )
                        )
                    }

                    is UiState.Failed -> {
                        Text(
                            text = "Failed to load bank details",
                            fontSize = 16.sp
                        )
                    }
                }

                BillsList()
            }

            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "+ Bill | Invoice",
                    fontSize = 20.sp
                )
            }
        }
    }
}
