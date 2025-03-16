package com.example.stockflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stockflow.presentation.screens.AddItemScreen
import com.example.stockflow.presentation.screens.AddPartyScreen
import com.example.stockflow.presentation.screens.DashBoardScreen
import com.example.stockflow.presentation.screens.InventoryScreen
import com.example.stockflow.presentation.screens.PartyScreen

@Composable
fun AppNavigator(
    navController: NavHostController = rememberNavController()
) {

    NavHost(navController = navController, startDestination = Screens.DashBoardScreen.route) {

        composable(
            route = Screens.DashBoardScreen.route
        ){
            DashBoardScreen(navController = navController)
        }

        composable(
            route = Screens.InventoryScreen.route
        ){
            InventoryScreen(navController = navController)
        }

        composable(
            route = Screens.PartyScreen.route
        ){
            PartyScreen(navController = navController)
        }

        composable(
            route = Screens.AddItemScreen.route
        ){
            AddItemScreen(navController = navController)
        }

        composable(
            route = Screens.AddPartyScreen.route
        ){
            AddPartyScreen(navController = navController)
        }
    }

}