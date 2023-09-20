package com.synaptic.xcorevpn.ui.components

import android.graphics.BlurMaskFilter
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.extensions.innerShadow
import com.synaptic.xcorevpn.extensions.shadow
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.ui.theme.LightColors


@Composable
fun VpnIndicator(vpnState: VpnState, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(200.dp)
            .height(200.dp)
            .vpnIndicatorOutsideShadow()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )

    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .background(color = MaterialTheme.colorScheme.onSurface, shape = CircleShape)
                //.vpnIndicatorInnerShadow()
        ) {
            Box(
                modifier = Modifier
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

fun Modifier.vpnIndicatorInnerShadow() = then(
    other = Modifier
        .innerShadow(
            color = LightColors.InnerShadowColor.copy(alpha = 0.2F),
            offsetX = (-1).dp,
            offsetY = 1.dp,
            blur = 2.dp,
            cornersRadius = 300.dp
        )
        .innerShadow(
            color = LightColors.InnerShadowColor.copy(alpha = 0.2F),
            offsetX = 1.dp,
            offsetY = (-1).dp,
            blur = 2.dp,
            cornersRadius = 300.dp
        )
        .innerShadow(
            color = Color.White.copy(alpha = 0.9F),
            offsetX = (-1).dp,
            offsetY = (-1).dp,
            blur = 2.dp,
            cornersRadius = 300.dp
        )
        .innerShadow(
            color = LightColors.InnerShadowColor.copy(alpha = 0.9F),
            offsetX = 1.dp,
            offsetY = 1.dp,
            blur = 3.dp,
            cornersRadius = 300.dp
        )
)

fun Modifier.vpnIndicatorOutsideShadow() = then(
    other = Modifier
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.2F),
            offsetX = (-5).dp,
            offsetY = 5.dp,
            blurRadius = 10.dp,
            borderRadius = 300.dp
        )
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.2F),
            offsetX = 5.dp,
            offsetY = (-5).dp,
            blurRadius = 10.dp,
            borderRadius = 300.dp
        )
        .shadow(
            color = Color.White.copy(alpha = 0.9F),
            offsetX = (-5).dp,
            offsetY = (-5).dp,
            blurRadius = 10.dp,
            borderRadius = 300.dp
        )
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.9F),
            offsetX = 5.dp,
            offsetY = 5.dp,
            blurRadius = 13.dp,
            borderRadius = 300.dp
        )
)

@Preview
@Composable
private fun VpnIndicatorPreview() {
    VpnIndicator(vpnState = VpnState.NoConfigFile)
}