package com.example.stockflow.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryList() {

    val categories = listOf(
        "Category 1",
        "Category 2",
        "Category 3"
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(categories.size) { index ->
            CategoryCard(name = categories[index])
        }
    }

}

@Composable
fun CategoryCard(name: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, Color.White, RoundedCornerShape(16.dp))
            .padding(4.dp)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f)
        )
        Row{
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Category"
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Category"
                )
            }
        }
    }

}
