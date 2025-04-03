package com.example.stockflow.presentation.navigation

sealed class Screens(val route: String) {

    data object UserScreen : Screens(route = "user_screen")
    data object EditUserScreen : Screens(route = "edit_user_screen")
    data object DashBoardScreen : Screens(route = "dash_board_screen")
    data object InventoryScreen : Screens(route = "inventory_screen")
    data object PartyScreen : Screens(route = "party_screen")
    data object AddItemScreen : Screens(route = "add_item_screen")
    data object AddPartyScreen : Screens(route = "add_party_screen")
    data object AddCategoryScreen : Screens(route = "add_category_screen")
    data object LoginScreen : Screens(route = "login_screen")

    data object BillScreen : Screens(route = "bill_screen")
    data object DayBookReportScreen : Screens(route = "day_book_report_screen")
    data object MoneyReportScreen : Screens(route = "money_report_screen")
    data object StockReportScreen : Screens(route = "stock_report_screen")
    data object TransactionReportScreen : Screens(route = "transaction_report_screen")
    data object BillDetailsScreen:  Screens(route = "bill_details_screen")
    data object AddBillScreen:  Screens(route = "add_bill_screen")

}