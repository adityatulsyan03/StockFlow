package com.example.stockflow.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stockflow.common.UiState
import com.example.stockflow.presentation.viewmodel.CategoryViewModel
import com.google.android.play.integrity.internal.c

@Composable
fun CategoryList(screen: String, categoryViewModel: CategoryViewModel) {

    val categoryState = categoryViewModel.getCategoriesState.collectAsState().value

    LaunchedEffect(screen) {
        categoryViewModel.getAllCategoriesByType(screen)
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

        when (categoryState) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            is UiState.Success -> {
                val categories = categoryState.data.data ?: emptyList()
                if (categories.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No categories Available",
                            color = Color.Gray
                        )
                    }
                }
                else{
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(categories.size) { index ->
                            CategoryCard(
                                name = categories[index].name,
                                onEditClick = { /* Handle edit */ },
                                onDeleteClick = { categories[index].id?.let {
                                    categoryViewModel.deleteCategoryById(
                                        it
                                    )
                                } }
                            )
                        }
                    }
                }
            }

            is UiState.Failed -> {
                Text(
                    text = "Error: ${categoryState.message}",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            is UiState.Idle -> {
//                categoryViewModel.getAllCategoriesByType(screen)
            }
        }
    }
}

@Composable
fun CategoryCard(
    name: String,
    onEditClick: () -> Unit,
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

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Category",
                    tint = Color(0xFFB0BEC5)
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Category",
                    tint = Color.Red
                )
            }
        }
    }
}