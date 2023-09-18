package com.synaptic.xcorevpn.ui.components.debug

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString



@Composable
fun CopyConfigButton(modifier: Modifier){
    val clipboardManager = LocalClipboardManager.current
    Button(onClick = {
        clipboardManager.setText(AnnotatedString("xcoredemo://import/https://vl.yagr.online/subscription/vless/8dd5beaa-6c17-4e55-b8fa-44ef137e6c37"))
    }) {
        Text(text = "Copy config to clipboard")
    }
}