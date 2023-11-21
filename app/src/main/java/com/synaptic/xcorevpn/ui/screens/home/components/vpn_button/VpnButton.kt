package com.synaptic.xcorevpn.ui.screens.home.components.vpn_button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.R
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.models.VpnState.*
import com.synaptic.xcorevpn.ui.components.RotationIcon
import com.synaptic.xcorevpn.ui.screens.home.HomeScreen
import com.synaptic.xcorevpn.ui.theme.LightColors

@Composable
fun VpnButton(vpnState: VpnState, modifier: Modifier = Modifier, onClick: () -> Unit) =
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = modifier
            .width((LocalConfiguration.current.screenHeightDp * 0.3).dp)
            .height((LocalConfiguration.current.screenHeightDp * 0.3).dp)
    ){
        Box(modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = LightColors.vpnButtonCenterCircleColor)
            .width((LocalConfiguration.current.screenHeightDp * 0.24).dp)
            .height((LocalConfiguration.current.screenHeightDp * 0.24).dp)

        ) {
            if(vpnState == Connecting){
                RotationCircleBorder()
            }
            if(vpnState == Active){
                PulseCircle()
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = Color.White)
                .width(140.dp)
                .height(140.dp)
                .align(Alignment.Center)
            ){
                when(vpnState){
                    Active -> VpnButtonIC(R.drawable.active_vpn)
                    Disable -> VpnButtonIC(R.drawable.vpn_off)
                    Connecting -> RotationIcon(R.drawable.loading_vpn)
                    NoConfigFile -> VpnButtonIC(R.drawable.vpn_off)
                    Unknown -> VpnButtonIC(R.drawable.error_circle)
                }
            }
        }

    }

@Preview
@Composable
private fun VpnButtonPreview() {
    VpnButton(vpnState = Active, modifier = Modifier){

    }
}