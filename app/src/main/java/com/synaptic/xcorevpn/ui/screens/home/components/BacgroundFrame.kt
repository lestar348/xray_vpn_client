package com.synaptic.xcorevpn.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.ui.screens.home.HomeScreen
import com.synaptic.xcorevpn.ui.theme.LightColors

@Composable
fun BackgroundFrame(){
        Box(modifier = Modifier
            .height((LocalConfiguration.current.screenHeightDp * 0.32).dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(0.dp, 0.dp, 50.dp, 50.dp))
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        LightColors.bgFrameLinearGradient1Color,
                        LightColors.bgFrameLinearGradient2Color
                    )
                )
            )
        )

}

@Preview
@Composable
private fun BackgroundFramePreview() {
    BackgroundFrame()
}