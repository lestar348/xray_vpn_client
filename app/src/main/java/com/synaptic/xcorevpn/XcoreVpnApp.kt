package com.synaptic.xcorevpn


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.synaptic.xcorevpn.services.navigation.AppRouts
import com.synaptic.xcorevpn.services.navigation.NavigationService
import com.synaptic.xcorevpn.services.navigation.navigationGraph
import com.synaptic.xcorevpn.util.MmkvManager

@Composable
fun XcoreApp(navController: NavHostController = rememberNavController()) {
    NavigationService.shared = NavigationService(navController = navController)
    val userID = MmkvManager.getUserID()
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        NavHost(
            navController = NavigationService.shared.navController,
            startDestination = if (userID == null) {
                AppRouts.Login.name
            } else {
                AppRouts.Home.name
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            navigationGraph()
        }

    }
}