package com.example.stockflow.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun TabSwitcher(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    tabs: List<String>
) {

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color(0xFF1E1E1E),
        contentColor = Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp)),
        indicator = { tabPositions ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF37474F))
                    .zIndex(-1f)
            )
        },
        divider = {}
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        title,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedTabIndex == index) Color.White else Color(0xFFB0BEC5) // Light gray for unselected tabs
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color(0xFFB0BEC5),
            )
        }
    }
}