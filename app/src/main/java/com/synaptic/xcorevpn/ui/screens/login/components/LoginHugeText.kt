package com.synaptic.xcorevpn.ui.screens.login.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun LoginHugeText(text: String) = Text(text = text,
    fontSize = 28.sp,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold,
    color = Color.White)