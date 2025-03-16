package com.example.stockflow.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.stockflow.presentation.components.AppScaffold
import com.example.stockflow.presentation.components.CategoryList
import com.example.stockflow.presentation.components.FAB
import com.example.stockflow.presentation.components.PartyList
import com.example.stockflow.presentation.components.TabSwitcher
import com.example.stockflow.presentation.components.TopBar
import com.example.stockflow.presentation.navigation.BottomNavBar
import com.example.stockflow.presentation.navigation.Screens

@Composable
fun PartyScreen(navController: NavController) {

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        bottomBar = {
            BottomNavBar(
                navController = navController
            )
        },
        topBar = {
            TopBar(
                title = "Party",
                navigationIcon = Icons.Outlined.ArrowBackIosNew
            )
        },
        floatingActionButton = {
            FAB(
                onClick =  {
                    navController.navigate(Screens.AddPartyScreen.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier,
                text = "ADD CUSTOMER/PARTY",
                extended = true,
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TabSwitcher(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index -> selectedTabIndex = index },
                tabs = listOf("PARTIES", "CATEGORIES")
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedTabIndex == 0) {
                PartyList()
            } else {
                CategoryList()
            }
        }

    }

}