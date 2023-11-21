package com.synaptic.xcorevpn.ui.screens.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.ui.theme.LightColors

@Composable
fun StatusText(vpnState: VpnState, modifier: Modifier) {
    var color = when(vpnState){
        VpnState.Active -> LightColors.accentGreen
        VpnState.Disable -> LightColors.black.copy(alpha = 0.3f)
        VpnState.Connecting -> LightColors.purple
        VpnState.NoConfigFile -> LightColors.black
        VpnState.Unknown -> LightColors.red
    }
    Text(text = vpnState.statusText, color = color, modifier = modifier)
}