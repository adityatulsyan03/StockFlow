package com.example.stockflow.presentation.screens.inventory

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.utils.safePopBackStack
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemDetailScreen(item: Inventory, navController: NavHostController) {

    Log.d("Screen","Detail Inventory Screen")

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val expireDate = item.expireDate?.let {
        try {
            LocalDate.parse(it, formatter)
        } catch (_: Exception) {
            null
        }
    }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Item Details",
                navigationIcon = Icons.Outlined.ArrowBackIosNew,
                onNavigationClick = { navController.safePopBackStack() },
                navigationIconContentDescription = "Back"
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Text(text = "Image", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Name: ${item.name}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Quantity: ${item.quantity}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Sell Price: ₹${item.sellPrice}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Sell Price Unit: ${item.sellPriceUnit}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Category: ${item.itemCategory}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "MRP: ₹${item.mrp}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Purchase Price: ₹${item.purchasePrice}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Tax: ${item.tax?.toString() ?: "Not Available"}%", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Item Code: ${item.itemCode ?: "Not Available"}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Barcode: ${item.barcode ?: "Not Available"}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Description: ${item.itemDescription ?: "Not Available"}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Low Stock Alert: ${item.lowStockAlert?.toString() ?: "Not Set"}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Storage Location: ${item.storageLocation ?: "Not Available"}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(
                    text = "Expiry Date: ${expireDate?.format(formatter) ?: "Not Available"}",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}