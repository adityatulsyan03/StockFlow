package com.example.stockflow.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockflow.data.model.Bills
import com.example.stockflow.presentation.screens.AddBillScreen
import com.example.stockflow.presentation.screens.AddCategoryScreen
import com.example.stockflow.presentation.screens.AddItemScreen
import com.example.stockflow.presentation.screens.AddPartyScreen
import com.example.stockflow.presentation.screens.AddSellingUnitScreen
import com.example.stockflow.presentation.screens.BillDetailsScreen
import com.example.stockflow.presentation.screens.BillsScreen
import com.example.stockflow.presentation.screens.DashBoardScreen
import com.example.stockflow.presentation.screens.DataTableScreen
import com.example.stockflow.presentation.screens.EditUserScreen
import com.example.stockflow.presentation.screens.InventoryScreen
import com.example.stockflow.presentation.screens.LoginScreen
import com.example.stockflow.presentation.screens.MoneyReportScreen
import com.example.stockflow.presentation.screens.PartyScreen
import com.example.stockflow.presentation.screens.StockReportScreen
import com.example.stockflow.presentation.screens.TransactionReportScreen
import com.example.stockflow.presentation.screens.UserScreen
import com.example.stockflow.presentation.viewmodel.BillsViewModel
import com.example.stockflow.presentation.viewmodel.CategoryViewModel
import com.example.stockflow.presentation.viewmodel.InventoryViewModel
import com.example.stockflow.presentation.viewmodel.PartyViewModel
import com.example.stockflow.presentation.viewmodel.ReportViewModel
import com.example.stockflow.presentation.viewmodel.SellingUnitViewModel
import com.example.stockflow.presentation.viewmodel.UserDetailViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(
    navController: NavHostController = rememberNavController()
) {

    val user by remember { mutableStateOf(Firebase.auth.currentUser) }

    val start = if (user != null) Screens.DashBoardScreen.route else Screens.LoginScreen.route

    val userDetailViewModel: UserDetailViewModel = hiltViewModel()
    val inventoryViewModel: InventoryViewModel = hiltViewModel()
    val partyViewModel: PartyViewModel = hiltViewModel()
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val billViewModel: BillsViewModel = hiltViewModel()
    val reportViewModel: ReportViewModel = hiltViewModel()
    val sellingUnitViewModel: SellingUnitViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = start) {

        composable(
            route = Screens.UserScreen.route
        ) {
            UserScreen(
                navController = navController,
                viewModel = userDetailViewModel
            )
        }

        composable(
            route = Screens.EditUserScreen.route
        ) {
            EditUserScreen(
                navController = navController,
                viewModel = userDetailViewModel
            )
        }

        composable(
            route = Screens.DashBoardScreen.route
        ) {
            DashBoardScreen(
                navController = navController,
                viewModel = userDetailViewModel,
                billViewModel = billViewModel
            )
        }

        composable(
            route = Screens.InventoryScreen.route
        ) {
            InventoryScreen(
                navController = navController,
                viewModel = inventoryViewModel,
                categoryViewModel = categoryViewModel
            )
        }

        composable(
            route = Screens.PartyScreen.route
        ) {
            PartyScreen(
                navController = navController,
                viewModel = partyViewModel,
                categoryViewModel = categoryViewModel
            )
        }

        composable(
            route = Screens.AddItemScreen.route
        ) {
            AddItemScreen(
                navController = navController,
                viewModel = inventoryViewModel,
                sellingUnitViewModel = sellingUnitViewModel
            )
        }

        composable(
            route = Screens.AddSellingUnit.route
        ){
            AddSellingUnitScreen(
                navController = navController,
                viewModel = sellingUnitViewModel
            )
        }

        composable(
            route = Screens.AddPartyScreen.route
        ) {
            AddPartyScreen(
                navController = navController,
                viewModel = partyViewModel
            )
        }

        composable(
            route = "${Screens.AddCategoryScreen.route}/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: ""

            AddCategoryScreen(
                navController = navController,
                viewModel = categoryViewModel,
                type = type
            )
        }

        composable(
            route = Screens.LoginScreen.route
        ) {
            LoginScreen(
                viewModel = userDetailViewModel,
                navController = navController
            )
        }

        composable(
            route = Screens.DayBookReportScreen.route
        ) {
            DataTableScreen(
                navController = navController,
                viewModel = reportViewModel
            )
        }

        composable(
            route = Screens.BillScreen.route
        ) {
            BillsScreen(
                navController = navController,
                viewModel = billViewModel
            )
        }

        composable(
            route = Screens.MoneyReportScreen.route
        ) {
            MoneyReportScreen(
                navController = navController,
                viewModel = reportViewModel
            )
        }

        composable(
            route = Screens.StockReportScreen.route
        ) {
            StockReportScreen(
                navController = navController,
                viewModel = reportViewModel
            )
        }

        composable(
            route = Screens.TransactionReportScreen.route
        ) {
            TransactionReportScreen(
                navController = navController,
                viewModel = reportViewModel
            )
        }

        composable(
            route = "${Screens.BillDetailsScreen.route}/{billJson}",
            arguments = listOf(
                navArgument("billJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val gson = Gson()
            val billJson = backStackEntry.arguments?.getString("billJson") ?: ""
            val decodedBillJson = URLDecoder.decode(billJson, StandardCharsets.UTF_8.toString()) // Decode JSON
            val bill = gson.fromJson(decodedBillJson, Bills::class.java) // Convert JSON back to Bills object

            BillDetailsScreen(
                navController = navController,
                viewModel = billViewModel,
                bill = bill
            )
        }

        composable(
            route = Screens.AddBillScreen.route,
        ) {
            AddBillScreen(
                navController = navController,
                viewModel = billViewModel,
                partyViewModel = partyViewModel,
                inventoryViewModel = inventoryViewModel
            )
        }

    }

}