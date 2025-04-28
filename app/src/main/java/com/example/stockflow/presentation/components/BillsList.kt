package com.example.stockflow.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stockflow.data.model.Bills

@Composable
fun BillsList(
    bills: List<Bills>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "RECENT BILLS",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            bills.forEach {
                BillCard(
                    name = it.partyName,
                    amount = it.totalAmount.toString(),
                    date = it.billDate
                )
            }
        }
    }
}

@Composable
fun BillCard(name: String, amount: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1E1E)) // Darker background for contrast
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = amount,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFB0BEC5) // Light gray for balance
            )
        }

        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFB0BEC5)
        )
    }
}