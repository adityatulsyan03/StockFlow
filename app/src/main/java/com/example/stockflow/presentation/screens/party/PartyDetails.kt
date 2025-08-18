package com.example.stockflow.presentation.screens.party

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
import com.example.stockflow.data.model.Party
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.utils.safePopBackStack
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PartyDetailScreen(party: Party, navController: NavHostController) {

    Log.d("Screen","Party Detail Screen")

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dob = party.dob.let {
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
                title = "Party Details",
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
                Text(text = "Name: ${party.name}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Phone: ${party.phone}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Category: ${party.category}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Billing Address: ${party.address}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Delivery Address: ${party.deliveryAddress}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "Postal Code: ${party.deliveryPostalCode}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(text = "GST Number: ${party.gstNumber}", modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(
                    text = if (party.customerSupplier) "It's a Customer" else "It's a Supplier",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Text(
                    text = "Date of Birth: ${dob?.format(formatter) ?: "Not Available"}",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}