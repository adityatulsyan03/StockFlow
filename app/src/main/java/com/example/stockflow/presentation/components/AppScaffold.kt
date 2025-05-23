package com.example.stockflow.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.stockflow.ui.theme.StockFlowTheme


/**
 * This function is used to Give the Default Background Colors and Alignment
 *
 * @param modifier (Optional) Modifier can be passed from the parent function to this
 * @param body This is the Composable that should be inside this
 */
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    body: @Composable BoxScope.() -> Unit
) {

    StockFlowTheme {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            snackbarHost = snackbarHost,
            contentWindowInsets = contentWindowInsets
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = contentAlignment,
                content = body
            )
        }
    }
}