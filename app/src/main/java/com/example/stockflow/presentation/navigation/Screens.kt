package com.example.stockflow.presentation.navigation

sealed class Screens(val route: String) {

    data object DashBoardScreen : Screens(route = "dash_board_screen")
    data object InventoryScreen : Screens(route = "inventory_screen")
    data object PartyScreen : Screens(route = "party_screen")
    data object AddItemScreen : Screens(route = "add_item_screen")
    data object AddPartyScreen : Screens(route = "add_party_screen")

}