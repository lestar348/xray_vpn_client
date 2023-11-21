package com.synaptic.xcorevpn.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextButton(modifier: Modifier = Modifier, title: String, titleColor: Color = Color.White, onClick: () -> Unit) = Button(
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = titleColor),
    onClick = onClick) {
    Text(text=title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
}