package com.example.stockflow.presentation.screens.party

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.presentation.components.*
import com.example.stockflow.presentation.navigation.BottomNavBar
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.viewmodel.CategoryViewModel
import com.example.stockflow.presentation.viewmodel.PartyViewModel
import com.example.stockflow.utils.safeNavigateOnce
import com.example.stockflow.utils.safePopBackStack

@Composable
fun PartyScreen(
    navController: NavController,
    viewModel: PartyViewModel,
    categoryViewModel: CategoryViewModel
) {

    Log.d("Screen","Party Screen")

    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle?.get<Boolean>("refreshParty")) {
        val shouldRefresh = savedStateHandle?.get<Boolean>("refreshParty") ?: false
        if (shouldRefresh) {
            savedStateHandle.remove<Boolean>("refreshParty")
        }
    }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        bottomBar = { BottomNavBar(navController = navController) },
        topBar = {
            TopBar(
                title = "Party",
                navigationIcon = Icons.Outlined.ArrowBackIosNew,
                onNavigationClick = { navController.safePopBackStack() },
                navigationIconContentDescription = "Back"
            )
        },
        floatingActionButton = {
            FAB(
                onClick = {
                    val type = "PARTY"
                    if (selectedTabIndex == 0) {
                        navController.safeNavigateOnce(Screens.AddPartyScreen.route)
                    } else {
                        navController.safeNavigateOnce("${Screens.AddCategoryScreen.route}/$type")
                    }
                },
                text = if (selectedTabIndex == 0) "ADD CUSTOMER/PARTY" else "ADD CATEGORY",
                extended = true,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabSwitcher(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index -> viewModel.setSelectedTabIndex(index) },
                tabs = listOf("Parties", "Category")
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                when (selectedTabIndex) {
                    0 -> PartyList(viewModel,navController)
                    1 -> CategoryList("PARTY", categoryViewModel,navController)
                }
            }
        }
    }
}