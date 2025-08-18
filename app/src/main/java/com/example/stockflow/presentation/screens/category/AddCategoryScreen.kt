package com.example.stockflow.presentation.screens.category

import android.R.attr.singleLine
import android.R.attr.textStyle
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.Category
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.FAB
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.screens.user.LoadingScreen
import com.example.stockflow.presentation.viewmodel.CategoryViewModel
import com.example.stockflow.utils.safePopBackStack

@Composable
fun AddCategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel,
    type: String
) {

    Log.d("Screen","Add Category Screen")

    val addCategoryState by viewModel.addCategoryState.collectAsState()

//    LaunchedEffect(addCategoryState) {
//        if (addCategoryState is UiState.Success){
//            viewModel.getAllCategories()
//            navController.previousBackStackEntry
//                ?.savedStateHandle
//                ?.set("refreshCategory", true)
//            navController.safePopBackStack()
//            viewModel.resetAddCategoryState()
//        }
//    }

    var categoryName by remember { mutableStateOf("") }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        topBar = {
            TopBar(
                title = "Add Category",
                navigationIcon = Icons.Outlined.ArrowBackIosNew,
                navigationIconContentDescription = "Back",
                onNavigationClick = { navController.safePopBackStack() }
            )
        },
        floatingActionButton = {
            FAB(
                onClick = {
                    viewModel.addCategory(
                        listOf(
                            Category(
                                name = categoryName,
                                type = type
                            )
                        )
                    )
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
            when(addCategoryState){
                is UiState.Loading -> {
                    LoadingScreen()
                }
                is UiState.Idle -> {
                    Text(
                        text = "ADD CATEGORY",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedTextField(
                        value = categoryName,
                        onValueChange = { categoryName = it },
                        label = { Text("Category Name", color = Color.White) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.White)
                    )
                }
                is UiState.Success -> {
                    viewModel.getAllCategories()
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refreshCategory", true)
                    navController.safePopBackStack()
                    viewModel.resetAddCategoryState()
                }
                else -> {}
            }
        }
    }
}