package com.synaptic.xcorevpn.ui.screens.home.components.servers_button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synaptic.xcorevpn.R
import com.synaptic.xcorevpn.ui.screens.home.HomeScreen
import com.synaptic.xcorevpn.ui.theme.LightColors

@Composable
fun ServerButton(modifier: Modifier = Modifier, onClick: () -> Unit) = Button(
    onClick = onClick,
    modifier = modifier
        .height(56.dp)
        .fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
    contentPadding = PaddingValues(19.dp)
    ){
    Image(
        painter = painterResource(
            id = R.drawable.server_ic
        ),
        contentDescription = "Servers",
        contentScale = ContentScale.None,
        modifier = Modifier.padding(end = 10.dp)
    )
    Text(text = "Servers", color = LightColors.black, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.weight(1f))
    Text(text = "Change", color = LightColors.purple, fontSize = 11.sp, fontWeight = FontWeight.Medium)
    Image(
        painter = painterResource(
            id = R.drawable.right_arrow
        ),
        contentDescription = "right arrow",
        contentScale = ContentScale.None,
        modifier = Modifier.padding(start = 6.dp)
    )
}

@Preview
@Composable
private fun ServerButtonPreview() {
    ServerButton(onClick = {})
}