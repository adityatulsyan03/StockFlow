package com.example.stockflow.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar{
        val backStackEntry = navController.currentBackStackEntryAsState()

        for (option in BottomNavOption.bottomNavOptions) {
            val selected = backStackEntry.value?.destination?.route == option.route

            NavigationBarItem(
                selected = selected,
                onClick = { option.onOptionClicked(navController) },
                icon = {
                    val currentIcon = if (selected) {
                        option.selectedIcon
                    } else {
                        option.unselectedIcon
                    }

                    Icon(
                        imageVector = currentIcon,
                        contentDescription = option.label
                    )

                },
                label = { Text(text = option.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}