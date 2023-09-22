package com.synaptic.xcorevpn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.extensions.shadow
import com.synaptic.xcorevpn.ui.theme.LightColors

@Composable
fun RotatedRoundedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    resourceId: Int,
    contentDescription: String
) = Button(
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(size = 24.dp),
    modifier = modifier
        .rotate(-45F)
        .vpnButtonShadow()
) {
    Image(
        painter = painterResource(
            id = resourceId
        ),
        contentDescription = contentDescription,
        contentScale = ContentScale.None,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .rotate(45F)


    )
}

fun Modifier.vpnButtonShadow() = then(
    other = Modifier
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.2F),
            offsetY = 5.dp,
            blurRadius = 10.dp,
            borderRadius = 80.dp
        )
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.2F),
            offsetX = 5.dp,
            offsetY = (-5).dp,
            blurRadius = 10.dp, borderRadius = 80.dp
        )
        .shadow(
            color = Color.White.copy(alpha = 0.9F),
            offsetX = (-5).dp,
            offsetY = (-5).dp,
            blurRadius = 10.dp, borderRadius = 80.dp
        )
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.9F),
            offsetX = 5.dp,
            offsetY = 5.dp,
            blurRadius = 13.dp, borderRadius = 80.dp
        )
)