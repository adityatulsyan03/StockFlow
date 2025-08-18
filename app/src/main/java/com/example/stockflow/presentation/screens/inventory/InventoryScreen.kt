package com.example.stockflow.presentation.screens.inventory

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.CategoryList
import com.example.stockflow.presentation.components.FAB
import com.example.stockflow.presentation.components.ItemList
import com.example.stockflow.presentation.components.TabSwitcher
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.navigation.BottomNavBar
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.viewmodel.CategoryViewModel
import com.example.stockflow.presentation.viewmodel.InventoryViewModel
import com.example.stockflow.presentation.viewmodel.SellingUnitViewModel
import com.example.stockflow.utils.safeNavigateOnce
import com.example.stockflow.utils.safePopBackStack

@Composable
fun InventoryScreen(
    navController: NavController,
    viewModel: InventoryViewModel,
    categoryViewModel: CategoryViewModel,
    sellingUnitViewModel: SellingUnitViewModel
) {

    Log.d("Screen","Inventory Screen")

    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()

    val sellingUnitState by sellingUnitViewModel.sellingUnitsState.collectAsState()
    LaunchedEffect(sellingUnitState) {
        if (sellingUnitState is UiState.Idle){
            sellingUnitViewModel.getAllSellingUnits()
        }
    }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        bottomBar = { BottomNavBar(navController = navController) },
        topBar = {
            TopBar(
                title = "Inventory",
                navigationIcon = Icons.Outlined.ArrowBackIosNew,
                onNavigationClick = { navController.safePopBackStack() },
                navigationIconContentDescription = "Back"
            )
        },
        floatingActionButton = {
            FAB(
                onClick = {
                    val type = "INVENTORY"
                    if (selectedTabIndex == 0) {
                        navController.safeNavigateOnce(Screens.AddItemScreen.route)
                    } else {
                        navController.safeNavigateOnce("${Screens.AddCategoryScreen.route}/$type")
                    }
                },
                text = if (selectedTabIndex == 0) "ADD NEW ITEM" else "ADD CATEGORY",
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
                onTabSelected = {
                    index -> viewModel.setSelectedTabIndex(index)
                },
                tabs = listOf("Items", "Category")
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                when (selectedTabIndex) {
                    0 -> ItemList(viewModel, navController)
                    1 -> CategoryList(
                        screen = "INVENTORY",
                        categoryViewModel = categoryViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}