package com.synaptic.xcorevpn.ui.screens.main


import android.app.Activity
import android.net.VpnService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.ui.components.VpnButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.synaptic.xcorevpn.BuildConfig
import com.synaptic.xcorevpn.R
import com.synaptic.xcorevpn.XcoreAppScreen
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.ui.components.CircleButton
import com.synaptic.xcorevpn.ui.components.VpnIndicator
import com.synaptic.xcorevpn.ui.components.debug.CopyConfigButton


@Composable
fun MainScreen(
    onServerSettingsClick: () -> Unit,
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val vpnState by viewModel.vpnState.collectAsState()
    val context = LocalContext.current

    val requestVpnPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.activeVPN(context)
            }
        }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        VpnIndicator(
            vpnState = vpnState,
            modifier = Modifier.padding(bottom = 95.dp, top = 145.dp)
        )
        VpnButton(
            onClick = {
                if (vpnState == VpnState.Active) {
                    viewModel.disableVPN(context)
                } else if (vpnState == VpnState.Disable) {
                    if (!viewModel.activeVPN(context)) {
                        requestVpnPermission.launch(VpnService.prepare(context))
                    }
                }
            },
            vpnState = vpnState,
            modifier = modifier
        )
        if (BuildConfig.DEBUG) {
            CopyConfigButton(modifier = modifier)
        }
        CircleButton(
            imageResourceId = R.drawable.ic_settings_gear,
            onClick = onServerSettingsClick,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
        )

    }
}


@Preview(showSystemUi = true)
@Composable
fun StartOrderPreview() {
    MainScreen(
        onServerSettingsClick = {}
    )
}