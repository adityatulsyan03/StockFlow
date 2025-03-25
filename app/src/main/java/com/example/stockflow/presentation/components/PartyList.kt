package com.example.stockflow.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.CustomResponse
import com.example.stockflow.data.model.Party
import com.example.stockflow.presentation.viewmodel.PartyViewModel

@Composable
fun PartyList(viewModel: PartyViewModel) {
    val partiesState by viewModel.getAllPartiesState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "PARTY LIST",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when (partiesState) {
            is UiState.Loading -> {
                // Show loading indicator
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is UiState.Success -> {
                val parties = (partiesState as UiState.Success).data.data ?: emptyList()
                if (parties.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Parties Available",
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(parties.size) { index ->
                            val party = parties[index]
                            PartyCard(
                                name = party.name,
                                number = party.phone,
                                amount = "₹54" // Assuming `amount` is a field in `Party`
                            )
                        }
                    }
                }
            }

            is UiState.Failed -> {
                val errorMessage = (partiesState as UiState.Failed).message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red
                    )
                }
            }

            is UiState.Idle -> {
                viewModel.getAllParties()
            }
        }
    }
}

@Composable
fun PartyCard(name: String, number: String, amount: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1E1E)) // Dark theme background
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = number,
                color = Color(0xFFB0BEC5) // Light gray for balance
            )
        }

        Text(
            text = amount,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB0BEC5) // Keeping amount in contrast color
        )
    }
}