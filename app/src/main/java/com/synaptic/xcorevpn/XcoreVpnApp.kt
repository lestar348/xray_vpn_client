package com.synaptic.xcorevpn

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.synaptic.xcorevpn.services.navigation.AppRouts
import com.synaptic.xcorevpn.services.navigation.NavigationService
import com.synaptic.xcorevpn.services.navigation.navigationGraph
import com.synaptic.xcorevpn.ui.components.CircleButton
import com.synaptic.xcorevpn.ui.screens.main.MainScreen
import com.synaptic.xcorevpn.ui.screens.servers.ServerScreen
import com.synaptic.xcorevpn.ui.components.CircleButton
import com.synaptic.xcorevpn.ui.screens.home.HomeScreen
import com.synaptic.xcorevpn.ui.screens.home.implementation.HomeViewModelImp

enum class XcoreAppScreen() {
    Main,
    Servers,
}

@Composable
fun XcoreApp(navController: NavHostController = rememberNavController()) {
    NavigationService.shared = NavigationService(navController = navController)
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        NavHost(
            navController = NavigationService.shared.navController,
            startDestination = AppRouts.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            navigationGraph()
        }

    }
}