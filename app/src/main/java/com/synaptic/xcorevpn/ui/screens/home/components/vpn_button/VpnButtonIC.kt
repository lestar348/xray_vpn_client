package com.synaptic.xcorevpn.ui.screens.home.components.vpn_button

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun VpnButtonIC( imageResourceId: Int) = Image(
    painter = painterResource(
        id = imageResourceId
    ),
    contentDescription = "Vpn control button",
    contentScale = ContentScale.None
)