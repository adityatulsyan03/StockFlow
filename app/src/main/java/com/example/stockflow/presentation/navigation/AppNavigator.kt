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
import com.example.stockflow.data.model.Category
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.data.model.Party
import com.example.stockflow.presentation.screens.reports.AddBillScreen
import com.example.stockflow.presentation.screens.category.AddCategoryScreen
import com.example.stockflow.presentation.screens.category.EditCategoryScreen
import com.example.stockflow.presentation.screens.inventory.AddItemScreen
import com.example.stockflow.presentation.screens.inventory.EditItemScreen
import com.example.stockflow.presentation.screens.party.AddPartyScreen
import com.example.stockflow.presentation.screens.inventory.AddSellingUnitScreen
import com.example.stockflow.presentation.screens.reports.BillDetailsScreen
import com.example.stockflow.presentation.screens.reports.BillsScreen
import com.example.stockflow.presentation.screens.user.DashBoardScreen
import com.example.stockflow.presentation.screens.reports.DataTableScreen
import com.example.stockflow.presentation.screens.user.EditUserScreen
import com.example.stockflow.presentation.screens.inventory.InventoryScreen
import com.example.stockflow.presentation.screens.inventory.ItemDetailScreen
import com.example.stockflow.presentation.screens.party.EditPartyScreen
import com.example.stockflow.presentation.screens.party.PartyDetailScreen
import com.example.stockflow.presentation.screens.user.LoginScreen
import com.example.stockflow.presentation.screens.reports.MoneyReportScreen
import com.example.stockflow.presentation.screens.party.PartyScreen
import com.example.stockflow.presentation.screens.reports.StockReportScreen
import com.example.stockflow.presentation.screens.reports.TransactionReportScreen
import com.example.stockflow.presentation.screens.user.UserScreen
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
            partyViewModel.setSelectedTabIndex(0)
            inventoryViewModel.setSelectedTabIndex(0)
            DashBoardScreen(
                navController = navController,
                viewModel = userDetailViewModel,
                billViewModel = billViewModel
            )
        }

        composable(
            route = Screens.InventoryScreen.route
        ) {
            partyViewModel.setSelectedTabIndex(0)
            InventoryScreen(
                navController = navController,
                viewModel = inventoryViewModel,
                sellingUnitViewModel = sellingUnitViewModel,
                categoryViewModel = categoryViewModel
            )
        }

        composable(
            route = Screens.PartyScreen.route
        ) {
            inventoryViewModel.setSelectedTabIndex(0)
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
                sellingUnitViewModel = sellingUnitViewModel,
                categoryViewModel = categoryViewModel
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
            route = "${Screens.ItemDetailScreen.route}/{itemJson}",
            arguments = listOf(navArgument("itemJson") {type = NavType.StringType })
        ){backStackEntry ->

            val gson = Gson()
            val itemJson = backStackEntry.arguments?.getString("itemJson") ?: ""
            val decodedItemJson = URLDecoder.decode(itemJson,StandardCharsets.UTF_8.toString())
            val item = gson.fromJson(decodedItemJson,Inventory::class.java)

            ItemDetailScreen(item = item,navController = navController)

        }

        composable(
            route = "${Screens.EditItemScreen.route}/{itemJson}",
            arguments = listOf(navArgument("itemJson") {type = NavType.StringType })
        ){backStackEntry ->

            val gson = Gson()
            val itemJson = backStackEntry.arguments?.getString("itemJson") ?: ""
            val decodedItemJson = URLDecoder.decode(itemJson,StandardCharsets.UTF_8.toString())
            val item = gson.fromJson(decodedItemJson,Inventory::class.java)

            EditItemScreen(item = item, navController = navController, inventoryViewModel, sellingUnitViewModel)
        }

        composable(
            route = Screens.AddPartyScreen.route
        ) {
            AddPartyScreen(
                navController = navController,
                viewModel = partyViewModel,
                categoryViewModel = categoryViewModel
            )
        }

        composable(
            route = "${Screens.PartyDetailScreen.route}/{partyJson}",
            arguments = listOf(navArgument("partyJson") {type = NavType.StringType })
        ){ backStackEntry ->
            val gson = Gson()
            val partyJson = backStackEntry.arguments?.getString("partyJson") ?: ""
            val decodedPartyJson = URLDecoder.decode(partyJson, StandardCharsets.UTF_8.toString()) // Decode JSON
            val party = gson.fromJson(decodedPartyJson, Party::class.java)

            PartyDetailScreen(party = party,navController = navController)
        }

        composable(
            route = "${Screens.EditPartyScreen.route}/{partyJson}",
            arguments = listOf(navArgument("partyJson") {type = NavType.StringType })
        ){backStackEntry ->
            val gson = Gson()
            val partyJson = backStackEntry.arguments?.getString("partyJson") ?: ""
            val decodedPartyJson = URLDecoder.decode(partyJson,StandardCharsets.UTF_8.toString())
            val party = gson.fromJson(decodedPartyJson,Party::class.java)

            EditPartyScreen(
                party = party,
                navController = navController,
                viewModel = partyViewModel,
                categoryViewModel = categoryViewModel
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
            route = "${Screens.EditCategoryScreen.route}/{categoryJson}",
            arguments = listOf(navArgument("categoryJson") {type = NavType.StringType })
        ){backStackEntry ->

            val gson = Gson()
            val categoryJson = backStackEntry.arguments?.getString("categoryJson") ?: ""
            val decodedCategoryJson = URLDecoder.decode(categoryJson,StandardCharsets.UTF_8.toString())
            val category = gson.fromJson(decodedCategoryJson,Category::class.java)

            EditCategoryScreen(
                category = category,
                viewModel = categoryViewModel,
                navController = navController
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
                reportViewModel = reportViewModel,
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
                inventoryViewModel = inventoryViewModel,
                reportViewModel = reportViewModel
            )
        }

    }

}