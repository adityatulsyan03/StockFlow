package com.example.stockflow.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Party
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.screens.user.LoadingScreen
import com.example.stockflow.presentation.viewmodel.PartyViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.annotation.meta.When

@Composable
fun PartyList(viewModel: PartyViewModel, navController: NavController) {

    val partiesState by viewModel.getAllPartiesState.collectAsState()
    val partyDeletedStatus by viewModel.deletePartyState.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            viewModel.getAllParties()
            isRefreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { isRefreshing = true })
    {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "PARTY LIST",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            when(partyDeletedStatus){
                is UiState.Loading -> {
                    LoadingScreen()
                }
                is UiState.Success -> {
                    Log.d("Launched Effect","partyDeletedState")
                    viewModel.getAllParties()
                    viewModel.resetDeletePartyState()
                }
                else -> {}
            }
            when (partiesState) {
                is UiState.Loading -> {
                    Log.d("Party State", "UiState Loading")
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                is UiState.Success -> {
                    Log.d("Party State", "UiState Success")
                    val parties = (partiesState as UiState.Success).data.data ?: emptyList()
                    if (parties.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No Parties Available", color = Color.Gray
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
                                    party = party,
                                    navController = navController,
                                    onDeleted = {
                                        viewModel.deletePartyById(it)
                                        Log.d("Party To Delete",it)
                                    })
                            }
                        }
                    }
                }

                is UiState.Failed -> {

                    Log.d("Party State", "UiState Failed")
                    val errorMessage = (partiesState as UiState.Failed).message
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: $errorMessage", color = Color.Red
                        )
                    }
                }

                is UiState.Idle -> {
                    Log.d("Party State", "UiState Idle")
                    viewModel.getAllParties()
                }
            }
        }
    }
}

@Composable
fun PartyCard(
    party: Party,
    navController: NavController,
    onDeleted: (String) -> Unit
) {
    val gson = Gson()
    val partyJson = gson.toJson(party)
    val encodedPartyJson = URLEncoder.encode(
        partyJson, StandardCharsets.UTF_8.toString()
    )
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(Color(0xFF1E1E1E)) // Dark theme background
        .clickable {
            navController.navigate("${Screens.PartyDetailScreen.route}/$encodedPartyJson")
        }
        .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(
                text = party.name, fontWeight = FontWeight.Bold, color = Color.White
            )
            Text(
                text = party.phone, color = Color(0xFFB0BEC5) // Light gray for balance
            )
        }
        Row{
            IconButton(
                onClick = {
                    var a = party.id ?: ""
                    onDeleted(a)
                    Log.d("Party Id", a)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
            IconButton(
                onClick = {
                    navController.navigate("${Screens.EditPartyScreen.route}/$encodedPartyJson")
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