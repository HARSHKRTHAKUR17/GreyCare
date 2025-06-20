package com.example.greycare

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "vitals") {
        composable("vitals") { VitalsScreen(navController) }
        composable("history") { VitalsHistoryScreen(navController) }
    }
}
