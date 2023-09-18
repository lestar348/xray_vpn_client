package com.synaptic.xcorevpn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.R
import com.synaptic.xcorevpn.models.VpnState


@Composable
fun VpnButton(onClick: () -> Unit, vpnState: VpnState, modifier: Modifier = Modifier) =
    Column {
        Button(
            onClick = onClick,

            modifier = modifier
                .shadow(
                    elevation = 13.dp,
                    spotColor = Color(0xE5CECFD4),
                    ambientColor = Color(0xE5CECFD4)
                )
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0xE5FFFFFF),
                    ambientColor = Color(0xE5FFFFFF)
                )
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x33CECFD4),
                    ambientColor = Color(0x33CECFD4)
                )
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x33CECFD4),
                    ambientColor = Color(0x33CECFD4)
                )
                .width(100.dp)
                .height(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 24.dp)
                )
        ) {
            Image(
                painter = painterResource(
                    id = when (vpnState) {
                        VpnState.Active -> R.drawable.ic_power_off
                        VpnState.Disable -> R.drawable.ic_power
                        else -> {
                            R.drawable.shield_tick
                        }
                    }
                ),
                contentDescription = "Vpn controll button",
                contentScale = ContentScale.None
            )
        }
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
            textAlign = TextAlign.Center
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