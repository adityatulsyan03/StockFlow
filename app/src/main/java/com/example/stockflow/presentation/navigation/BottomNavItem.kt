package com.example.stockflow.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.stockflow.utils.navigateBottomBar

sealed class BottomNavOption(
    val route: String,
    val onOptionClicked: (NavController) -> Unit,
    val label: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
) {

    data object DashBoardScreen : BottomNavOption(
        route = Screens.DashBoardScreen.route,
        onOptionClicked = {
            it.navigateBottomBar(Screens.DashBoardScreen.route)
        },
        label = "Dashboard",
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home
    )

    data object PartyScreen : BottomNavOption(
        route = Screens.PartyScreen.route,
        onOptionClicked = {
            it.navigateBottomBar(Screens.PartyScreen.route)
        },
        label = "Party",
        unselectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person
    )

    data object InventoryScreen : BottomNavOption(
        route = Screens.InventoryScreen.route,
        onOptionClicked = {
            it.navigateBottomBar(Screens.InventoryScreen.route)
        },
        label = "Inventory",
        unselectedIcon = Icons.Outlined.Category,
        selectedIcon = Icons.Filled.Category
    )

    companion object {
        val bottomNavOptions = listOf(DashBoardScreen, PartyScreen, InventoryScreen)
    }
}