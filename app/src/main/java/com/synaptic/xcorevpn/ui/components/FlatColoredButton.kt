package com.synaptic.xcorevpn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.synaptic.xcorevpn.ui.theme.LightColors


@Composable
fun FlatColoredButton(
    modifier: Modifier = Modifier,
    title:String,
    titleColor: Color = Color.White,
    backgroundColor: Color = LightColors.black,
    disableColor: Color = LightColors.black.copy(alpha = 0.2f),
    iconResId: Int? = null,
    enabled: Boolean, onClick: () -> Unit) = Button(
    onClick = onClick,
    enabled = enabled,
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, disabledContainerColor = disableColor),
    shape = RoundedCornerShape(100.dp)
    ) {
    Text(text = title, fontWeight = FontWeight.Medium, fontSize = 20.sp, color = titleColor)
    if(iconResId != null){
        Image(
            painter = painterResource(
                id = iconResId
            ),
            contentDescription = "Servers",
            contentScale = ContentScale.None,
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}

@Preview
@Composable
private fun FlatColoredButtonPreview() {
    FlatColoredButton(title = "Enter", enabled = true, onClick = {} )
}