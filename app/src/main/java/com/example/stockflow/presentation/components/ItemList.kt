package com.example.stockflow.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stockflow.R

@Composable
fun ItemList() {

    val items = listOf(
        listOf("laptop", "300", "10000"),
        listOf("laptop", "300", "10000"),
        listOf("laptop", "300", "10000"),
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items.size) { index ->
            ItemCard(
                name = items[index][0], quantity = items[index][1], price = items[index][2]
            )
        }
    }

}

@Composable
fun ItemCard(
    name: String, quantity: String, price: String
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable {  }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = name,
                modifier = Modifier
                    .padding(4.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Red)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
                Text(
                    text = quantity,
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                Text(
                    text = price,
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }
        }
    }

}
