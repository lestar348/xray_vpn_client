package com.synaptic.xcorevpn.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.R
import com.synaptic.xcorevpn.models.VpnState


@Composable
fun VpnButton(onClick: () -> Unit, vpnState: VpnState, modifier: Modifier = Modifier) =
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        RotatedRoundedButton(
            modifier = modifier
                .heightIn(min = 100.dp, max = 100.dp)
                .widthIn(min = 100.dp, max = 100.dp)
                .padding(bottom = 10.dp),
            onClick = onClick,
            resourceId = when (vpnState) {
                VpnState.Active -> R.drawable.ic_power_off
                VpnState.Disable -> R.drawable.ic_power
                else -> {
                    R.drawable.shield_tick
                }
            },
            contentDescription = "Vpn control button"
        )
        Text(
            text = when (vpnState) {
                VpnState.Active -> "VPN ON"
                VpnState.Disable -> "VPN OFF"
                VpnState.NoConfigFile -> "Need accept VPN Configuration"
                VpnState.Connecting -> "CONNECTING"
                else -> {
                    ""
                }
            },
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 20.dp)
        )
    }


@Preview
@Composable
fun StartOrderPreview() {
    VpnButton(
        onClick = {},
        vpnState = VpnState.Active,
    )
}