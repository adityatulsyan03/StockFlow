package com.example.stockflow.presentation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DensityMedium
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.FAB
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.navigation.BottomNavBar

@Composable
fun DashBoardScreen(navController: NavController) {

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        bottomBar = {
            BottomNavBar(
                navController = navController
            )
        },
        topBar = {
            TopBar(
                title = "DashBoard",
                navigationIcon = Icons.Outlined.DensityMedium
            )
        }
    ) {

    }

}