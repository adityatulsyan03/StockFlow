package com.example.stockflow.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.navigation.NavController
import com.example.stockflow.R
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Inventory
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.screens.user.LoadingScreen
import com.example.stockflow.presentation.viewmodel.InventoryViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ItemList(viewModel: InventoryViewModel, navController: NavController) {
    val inventoryState by viewModel.getAllInventoriesState.collectAsState()
    val inventoryDeletedState by viewModel.deleteInventoryState.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(inventoryDeletedState) {
        if (inventoryDeletedState is UiState.Success){
            Log.d("Launched Effect","inventoryDeletedState")
            viewModel.getAllInventories()
            viewModel.resetDeleteItemState()
        }
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            viewModel.getAllInventories()
            isRefreshing = false
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "INVENTORY ITEMS",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { isRefreshing = true }
        ) {
            when (inventoryState) {
                is UiState.Loading -> {
                    LoadingScreen()
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
                                    price = "₹${items[index].sellPrice}",
                                    item = items[index],
                                    navController = navController,
                                    onDelete = {
                                        viewModel.deleteInventoryById(it)
                                    }
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
}

@Composable
fun ItemCard(
    name: String,
    quantity: String,
    price: String,
    item: Inventory,
    navController: NavController,
    onDelete: (String) -> Unit
) {
    val gson = Gson()
    val itemJson = gson.toJson(item)
    val encodedItem = URLEncoder.encode(
        itemJson,
        StandardCharsets.UTF_8.toString()
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1E1E))
            .clickable {
                navController.navigate("${Screens.ItemDetailScreen.route}/$encodedItem")

            }
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

        Row{
            IconButton(
                onClick = {
                    onDelete(item.id ?: "")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }

            IconButton(
                onClick = {
                    navController.navigate("${Screens.EditItemScreen.route}/$encodedItem")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }

    }
}