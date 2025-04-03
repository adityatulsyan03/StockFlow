package com.example.stockflow.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stockflow.common.UiState
import com.example.stockflow.presentation.viewmodel.CategoryViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CategoryList(screen: String, categoryViewModel: CategoryViewModel) {
    val categoryState by categoryViewModel.getCategoriesState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            categoryViewModel.getAllCategories()
            isRefreshing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "CATEGORIES",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { isRefreshing = true } // Trigger refresh
        ) {
            when (categoryState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                is UiState.Success -> {
                    val categories = (categoryState as UiState.Success).data.data ?: emptyList()
                    val typeCategory = categories.filter { it.type == screen }

                    if (typeCategory.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No categories Available",
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(typeCategory.size) { index ->
                                CategoryCard(
                                    name = typeCategory[index].name,
                                    onDeleteClick = {
                                        typeCategory[index].id?.let { categoryId ->
                                            categoryViewModel.deleteCategoryById(categoryId)
                                        }
                                        categoryViewModel.getAllCategories()
                                    }
                                )
                            }
                        }
                    }
                }

                is UiState.Failed -> {
                    Text(
                        text = "Error: ${(categoryState as UiState.Failed).message}",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                is UiState.Idle -> {
                    categoryViewModel.getAllCategories()
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    name: String,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1E1E))
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )

        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Category",
                tint = Color.Red
            )
        }
    }
}