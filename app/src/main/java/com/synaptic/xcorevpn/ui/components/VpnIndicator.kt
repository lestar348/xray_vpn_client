package com.synaptic.xcorevpn.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.models.VpnState


@Composable
fun VpnIndicator(vpnState: VpnState, modifier: Modifier = Modifier) {
    Box(
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
            .width(200.dp)
            .height(200.dp)
            .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = MaterialTheme.colorScheme.onSurface, shape = CircleShape)
                .padding(54.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        color = when (vpnState) {
                            VpnState.Active -> MaterialTheme.colorScheme.primary
                            VpnState.Disable -> MaterialTheme.colorScheme.error
                            else -> {
                                MaterialTheme.colorScheme.errorContainer
                            }
                        },
                        shape = CircleShape
                    )
                    .width(32.dp)
                    .height(32.dp)
            )
        }
    }
}

@Preview
@Composable
private fun VpnIndicatorPreview() {
    VpnIndicator(vpnState = VpnState.NoConfigFile)
}