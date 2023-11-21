package com.synaptic.xcorevpn.ui.screens.home

import android.app.Activity
import android.net.VpnService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.synaptic.xcorevpn.XcoreAppScreen
import com.synaptic.xcorevpn.models.ConnectProblem
import com.synaptic.xcorevpn.models.VPNConnectException
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.services.navigation.AppRouts
import com.synaptic.xcorevpn.services.navigation.NavigationService
import com.synaptic.xcorevpn.ui.screens.home.components.BackgroundFrame
import com.synaptic.xcorevpn.ui.screens.home.components.CurrentServer
import com.synaptic.xcorevpn.ui.screens.home.components.HomeAppBar
import com.synaptic.xcorevpn.ui.screens.home.components.StatusText
import com.synaptic.xcorevpn.ui.screens.home.components.SubscriptionCard
import com.synaptic.xcorevpn.ui.screens.home.components.servers_button.ServerButton
import com.synaptic.xcorevpn.ui.screens.home.components.vpn_button.VpnButton
import com.synaptic.xcorevpn.ui.screens.home.implementation.HomeViewModelPreveiwImp


@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel,){
    val vpnState by viewModel.vpnState.collectAsState()
    val currentLocation by viewModel.currentServerLocation.collectAsState()

    val context = LocalContext.current

    val requestVpnPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.toggleVPN(context)
            }
        }

    Scaffold (
       modifier = Modifier.background(
           color = MaterialTheme.colorScheme.background,
       ),
        topBar = { HomeAppBar() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
            Box(contentAlignment = Alignment.BottomCenter){
                BackgroundFrame()
                VpnButton(vpnState = vpnState,
                    modifier = Modifier.offset(y =(LocalConfiguration.current.screenHeightDp * 0.12).dp)
                    ) {
                    try {
                        viewModel.toggleVPN(context)
                    } catch (e: VPNConnectException){
                        if(e.problem == ConnectProblem.NeedAcceptPermission){
                            requestVpnPermission.launch(VpnService.prepare(context))
                        }
                    }
                }
            }
            StatusText(vpnState = vpnState, modifier = Modifier.padding(top = (LocalConfiguration.current.screenHeightDp * 0.11).dp, bottom = 76.dp))
            if(currentLocation != null){
                CurrentServer(location = currentLocation!!)
            }

            ServerButton(modifier = Modifier
                .padding(top = 30.dp)
                .padding(horizontal = 20.dp),
                onClick = {
                    NavigationService.shared.navigateTo(AppRouts.ServersList)
                })
            SubscriptionCard(modifier = Modifier
                .padding(top = 14.dp)
                .padding(horizontal = 20.dp), onClick = {})
        }
    }

}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(viewModel = HomeViewModelPreveiwImp())
}