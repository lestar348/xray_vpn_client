package com.synaptic.xcorevpn.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.ui.screens.home.components.vpn_button.VpnButton
import com.synaptic.xcorevpn.ui.theme.LightColors


@Composable
fun SubscriptionCard(modifier: Modifier = Modifier, onClick: () -> Unit)= Box(
    modifier = modifier
        .height(92.dp)
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(16.dp))
        .background(color = LightColors.purple)
        .clickable {
            // TODO go to telegramm
        }

){
    // TODO add subsctiption
}


@Preview
@Composable
private fun SubscriptionCardPreview() {
    SubscriptionCard( modifier = Modifier){

    }
}