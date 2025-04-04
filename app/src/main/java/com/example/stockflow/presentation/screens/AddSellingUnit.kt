package com.example.stockflow.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.FAB
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.viewmodel.SellingUnitViewModel
import com.example.stockflow.data.model.SellingUnit

@Composable
fun AddSellingUnitScreen(
    navController: NavController,
    viewModel: SellingUnitViewModel
) {
    var sellingUnitName by remember { mutableStateOf("") }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Add Selling Unit",
                navigationIcon = Icons.Outlined.ArrowBackIosNew,
                navigationIconContentDescription = "Back",
                onNavigationClick = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            FAB(
                onClick = {
                    if (sellingUnitName.isNotBlank()) {
                        viewModel.postSellingUnit(
                            listOf(
                                SellingUnit(unitName = sellingUnitName)
                            )
                        )
                        navController.popBackStack()
                    }
                },
                text = "ADD",
                extended = true
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ADD SELLING UNIT",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = sellingUnitName,
                onValueChange = { sellingUnitName = it },
                label = { Text("Selling Unit Name", color = Color.White) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White)
            )
        }
    }
}