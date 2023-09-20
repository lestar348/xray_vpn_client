package com.synaptic.xcorevpn.ui.screens.servers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.R
import com.synaptic.xcorevpn.extensions.shadow
import com.synaptic.xcorevpn.models.ServerConfig
import com.synaptic.xcorevpn.ui.components.circleButtonShadow
import com.synaptic.xcorevpn.ui.theme.LightColors

@Composable
fun ServerElement(
    modifier: Modifier = Modifier,
    server: ServerConfig,
    selected: Boolean,
    onClick: (ServerConfig) -> Unit,
) =
    Button(
        onClick = { onClick(server) },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(size = 14.dp),
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 15.dp)
            .fillMaxWidth()
            .serverElementShadow()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = server.remarks)
            Text(text = server.configType.name)
        }
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(
                id = if (selected) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_cloud_connection
                }
            ),
            contentDescription = "Select server button",
            contentScale = ContentScale.None,

            )
    }

fun Modifier.serverElementShadow() = then(
    other = Modifier.shadow(
        color = LightColors.dropShadowColor.copy(alpha = 0.2F),
        offsetX = (-5).dp,
        offsetY = 5.dp,
        blurRadius = 10.dp,
        borderRadius = 14.dp
    )
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.2F),
            offsetX = 5.dp,
            offsetY = (-5).dp,
            blurRadius = 10.dp,
            borderRadius = 14.dp
        )
        .shadow(
            color = Color.White.copy(alpha = 0.9F),
            offsetX = (-5).dp,
            offsetY = (-5).dp,
            blurRadius = 10.dp,
            borderRadius = 14.dp
        )
        .shadow(
            color = LightColors.dropShadowColor.copy(alpha = 0.9F),
            offsetX = 5.dp,
            offsetY = 5.dp,
            blurRadius = 13.dp,
            borderRadius = 14.dp
        )
)