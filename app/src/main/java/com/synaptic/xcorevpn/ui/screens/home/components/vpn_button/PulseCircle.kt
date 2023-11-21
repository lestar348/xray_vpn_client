package com.synaptic.xcorevpn.ui.screens.home.components.vpn_button

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun PulseCircle(){
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val heartPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween<Float>(
                durationMillis = 1500,
                easing = FastOutLinearInEasing,
            ),
        ), label = "pulse"
    )

    Box(modifier = Modifier
        .clip(shape = CircleShape)
        .background(color = Color(0xFFD1CEFF).copy(alpha = (1 - heartPulse) * 5.0f))
        .width(193.dp)
        .height(193.dp))
}

@Preview
@Composable
private fun PulseCirclePreview() {
    PulseCircle()
}