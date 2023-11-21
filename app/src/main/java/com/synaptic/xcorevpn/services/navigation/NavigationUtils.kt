package com.synaptic.xcorevpn.services.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.synaptic.xcorevpn.ui.screens.home.HomeScreen
import com.synaptic.xcorevpn.ui.screens.home.implementation.HomeViewModelImp
import com.synaptic.xcorevpn.ui.screens.servers.ServerScreen

enum class AppRouts() {
    Home,
    ServersList,
    Login,
    ConfirmEmail,
    Subscription,
    MyAccount,
    VpnSettings,
    LogPermission,
    ViewLogScreen,
    WebsitesScreen,
    RenewScreen,
}


fun NavGraphBuilder.navigationGraph(
    modifier: Modifier = Modifier,
) {
    composable(AppRouts.Home.name) {
        HomeScreen(viewModel = HomeViewModelImp())
    }
    composable(AppRouts.ServersList.name) {
        ServerScreen(onBackButtonClick = {})
    }

}
