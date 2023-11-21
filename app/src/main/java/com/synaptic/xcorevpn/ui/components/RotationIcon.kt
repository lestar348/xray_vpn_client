package com.synaptic.xcorevpn.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun RotationIcon(imageResourceId: Int){
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween<Float>(
                durationMillis = 300,
                easing = FastOutLinearInEasing,
            ),
        ), label = "rotationIcon"
    )

    Image(
        painter = painterResource(
            id = imageResourceId
        ),
        contentDescription = "Vpn control button",
        contentScale = ContentScale.None,
        modifier = Modifier
            .rotate(rotation)
    )
}