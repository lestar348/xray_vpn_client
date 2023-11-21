package com.synaptic.xcorevpn.ui.screens.login.components


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmEmailTip(email: String) = Text(
    text = "A code has been sent to $email\nPaste it in the field above",
    color = Color.White,
    fontSize = 12.sp,
    fontWeight = FontWeight.Normal
)