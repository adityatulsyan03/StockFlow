package com.example.stockflow.presentation.screens.user

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.DensityMedium
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Bank
import com.example.stockflow.data.model.Bills
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.User
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.BankDetails
import com.example.stockflow.presentation.components.BillsList
import com.example.stockflow.presentation.components.SidebarDrawer
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.navigation.BottomNavBar
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.viewmodel.BillsViewModel
import com.example.stockflow.presentation.viewmodel.UserDetailViewModel
import com.example.stockflow.utils.safeNavigateOnce
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun DashBoardScreen(
    navController: NavController,
    viewModel: UserDetailViewModel,
    billViewModel: BillsViewModel
) {

    Log.d("Screen", "DashBoard Screen")
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            viewModel.getUserData()
            viewModel.getBankDetails()
            billViewModel.getAllBills()
            isRefreshing = false
        }
    }

    val userState by viewModel.getUserState.collectAsState()
    val bankState by viewModel.bankState.collectAsState()
    val billState by billViewModel.billsState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = drawerState.isOpen) {
        coroutineScope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerContent = {
            SidebarDrawer(navController) {
                coroutineScope.launch { drawerState.close() }
            }
        }, drawerState = drawerState
    ) {
        AppScaffold(
            contentAlignment = Alignment.TopStart,
            bottomBar = { BottomNavBar(navController) },
            topBar = {
                TopBarContent(userState, navController, coroutineScope, drawerState, viewModel)
            }) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { isRefreshing = true }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    item {
                        UserStateContent(userState, viewModel)
                        Column {
                            BankStateContent(bankState, viewModel)
                            BillStateContent(billState, billViewModel)
                        }
                    }
                    item {
                        Button(
                            onClick = { navController.safeNavigateOnce(Screens.AddBillScreen.route) },
                        ) {
                            Text(text = "+ Bill | Invoice", fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BillStateContent(billState: UiState<CustomResponse<List<Bills>>>, billViewModel: BillsViewModel) {
    when (billState) {
        is UiState.Idle -> billViewModel.getAllBills()
        is UiState.Loading -> LoadingScreen()
        is UiState.Failed -> ErrorScreen()
        is UiState.Success -> {
            val bills = billState.data.data
            BillsList(bills?.subList(0, min(3, bills.size)) ?: emptyList())
        }
    }
}

@Composable
fun UserStateContent(userState: UiState<CustomResponse<User>>, viewModel: UserDetailViewModel) {
    when (userState) {
        is UiState.Idle -> viewModel.getUserData()
        is UiState.Loading -> LoadingScreen()
        is UiState.Failed -> ErrorScreen()
        is UiState.Success -> Unit
    }
}

@Composable
fun BankStateContent(bankState: UiState<CustomResponse<Bank>>, viewModel: UserDetailViewModel) {
    when (bankState) {
        is UiState.Idle -> viewModel.getBankDetails()
        is UiState.Loading -> LoadingScreen()
        is UiState.Failed -> ErrorScreen()
        is UiState.Success -> {
            val bank = bankState.data.data
            BankDetails(bank ?: Bank())
        }
    }
}

@Composable
fun TopBarContent(
    userState: UiState<CustomResponse<User>>,
    navController: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    viewModel: UserDetailViewModel
) {
    val user = (userState as? UiState.Success<CustomResponse<User>>)?.data?.data

    TopBar(
        title = user?.businessName ?: "User..!",
        navigationIcon = Icons.Outlined.DensityMedium,
        onNavigationClick = { coroutineScope.launch { drawerState.open() } },
        navigationIconContentDescription = "Open Sidebar",
        onTitleClick = {
            navController.safeNavigateOnce(Screens.UserScreen.route)
        },
        trailingIcon = Icons.Outlined.Clear,
        onTrailingClick = {
            viewModel.logout()
            Firebase.auth.signOut()
            Log.d("FireBase Auth", Firebase.auth.currentUser.toString())
            navController.navigate(Screens.LoginScreen.route) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        },
        trailingIconContentDescription = "Logout"
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp), color = Color.White
        )
    }
}

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Something went wrong",
            color = Color.White
        )
    }
}