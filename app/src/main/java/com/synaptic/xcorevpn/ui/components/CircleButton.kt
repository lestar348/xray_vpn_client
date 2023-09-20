package com.synaptic.xcorevpn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.R
import com.synaptic.xcorevpn.extensions.shadow
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.ui.theme.LightColors

@Composable
fun CircleButton(modifier: Modifier = Modifier, imageResourceId: Int, onClick: () -> Unit) = Button(
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = CircleShape,
    modifier = modifier.circleButtonShadow()
) {
    Image(
        painter = painterResource(
            id = imageResourceId
        ),
        contentDescription = "Vpn control button",
        contentScale = ContentScale.None,
    )
}

fun Modifier.circleButtonShadow() = then(
    other = Modifier.shadow(
        color = LightColors.dropShadowColor.copy(alpha = 0.2F),
        offsetX = (-5).dp,
        offsetY = 5.dp,
        blurRadius = 10.dp,
        borderRadius = 100.dp
    )
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.2F),
            offsetX = 5.dp,
            offsetY = (-5).dp,
            blurRadius = 10.dp,
            borderRadius = 100.dp
        )
        .shadow(
            color = Color.White.copy(alpha = 0.9F),
            offsetX = (-5).dp,
            offsetY = (-5).dp,
            blurRadius = 10.dp,
            borderRadius = 100.dp
        )
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.9F),
            offsetX = 5.dp,
            offsetY = 5.dp,
            blurRadius = 13.dp,
            borderRadius = 100.dp
        )
)

@Preview
@Composable
fun StartCircleButtonReview() {
    CircleButton(
        onClick = {},
        imageResourceId = R.drawable.ic_power
    )
}