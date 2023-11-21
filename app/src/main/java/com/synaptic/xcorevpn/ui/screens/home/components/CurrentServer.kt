package com.synaptic.xcorevpn.ui.screens.home.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.ui.screens.home.HomeScreen

@Composable
fun CurrentServer(modifier: Modifier = Modifier, location: String) = Box(
    modifier = modifier
    .height(38.dp)
    .clip(RoundedCornerShape(100.dp))
    .background(color = Color.White),
    contentAlignment = Alignment.Center
){
    Row(modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp)){
        //TODO add icon
        Text(text = location)
    }
}


@Preview
@Composable
private fun CurrentServerPreview() {
    CurrentServer(Modifier, location = "Poland")
}