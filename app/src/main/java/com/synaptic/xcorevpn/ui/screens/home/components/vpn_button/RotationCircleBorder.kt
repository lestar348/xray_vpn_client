package com.synaptic.xcorevpn.ui.screens.home.components.vpn_button

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.ui.theme.LightColors
import java.util.Collections.rotate

@Composable
fun RotationCircleBorder(){
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween<Float>(
                durationMillis = 300,
                easing = FastOutLinearInEasing,
            ),
        ), label = "color"
    )

    Box(modifier = Modifier.clip(shape = CircleShape)
        .width(193.dp)
        .height(193.dp)
        .border(width = 2.dp, brush = Brush.linearGradient(listOf(Color.White, LightColors.vpnButtonCenterCircleColor, LightColors.bgFrameLinearGradient1Color)), shape = CircleShape)
        .rotate(rotation)
    )
}

@Preview
@Composable
private fun RotationCircleBorderPreview() {
    RotationCircleBorder()
}