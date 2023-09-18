package com.synaptic.xcorevpn

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.synaptic.xcorevpn.ui.screens.main.MainScreen
import com.synaptic.xcorevpn.ui.screens.servers.ServerScreen

enum class XcoreAppScreen() {
    Main,
    Servers,
}

@Composable
fun XcoreApp(navController: NavHostController = rememberNavController()) {
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = XcoreAppScreen.Main.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = XcoreAppScreen.Main.name) {
                MainScreen(
                    onServerSettingsClick = { navController.navigate(XcoreAppScreen.Servers.name)}
                )
            }
            composable(route = XcoreAppScreen.Servers.name) {
                ServerScreen(
                    onBackButtonClick = { navController.popBackStack() }
                )
            }

        }

    }
}