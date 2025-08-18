package com.example.stockflow.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

private val isNavigating = AtomicBoolean(false)

fun NavController.safeNavigateOnce(route: String) {
    if (isNavigating.get()) return

    isNavigating.set(true)
    this.navigate(route) {
        launchSingleTop = true
    }

    CoroutineScope(Dispatchers.Main).launch {
        delay(500) // Adjust debounce time if needed
        isNavigating.set(false)
    }
}

private val isBackNavigating = AtomicBoolean(false)

fun NavController.safePopBackStack(): Boolean {
    if (isBackNavigating.get()) return false

    isBackNavigating.set(true)
    val result = this.popBackStack()

    CoroutineScope(Dispatchers.Main).launch {
        delay(500) // Same debounce window
        isBackNavigating.set(false)
    }

    return result
}

fun NavController.navigateBottomBar(route: String) {
    val currentRoute = this.currentDestination?.route
    if (currentRoute == route) return

    this.navigate(route) {
        popUpTo(this@navigateBottomBar.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}