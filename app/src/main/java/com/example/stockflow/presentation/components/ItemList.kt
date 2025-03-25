package com.example.stockflow.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockflow.R
import com.example.stockflow.common.UiState
import com.example.stockflow.presentation.viewmodel.InventoryViewModel

@Composable
fun ItemList(viewModel: InventoryViewModel) {
    val inventoryState by viewModel.getAllInventoriesState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "INVENTORY ITEMS",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when (inventoryState) {
            is UiState.Loading -> {
                // Show a loading indicator
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is UiState.Success -> {
                val items = (inventoryState as UiState.Success).data.data ?: emptyList()

                if (items.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No items found", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(items.size) { index ->
                            ItemCard(
                                name = items[index].name,
                                quantity = "${items[index].quantity} pcs",
                                price = "₹${items[index].sellPrice}"
                            )
                        }
                    }
                }
            }

            is UiState.Failed -> {
                val errorMessage = (inventoryState as UiState.Failed).message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: $errorMessage", color = Color.Red)
                }
            }

            is UiState.Idle -> {
                viewModel.getAllInventories()
            }
        }
    }
}

@Composable
fun ItemCard(name: String, quantity: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1E1E))
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Item Image
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = name,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
        )

        // Item Details
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp,
            )
            Text(
                text = "Quantity: $quantity",
                color = Color(0xFFB0BEC5),
                fontSize = 14.sp
            )
            Text(
                text = "Price: $price",
                color = Color(0xFFB0BEC5),
                fontSize = 14.sp
            )
        }
    }
}