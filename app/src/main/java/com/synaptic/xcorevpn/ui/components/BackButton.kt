package com.synaptic.xcorevpn.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.synaptic.xcorevpn.R


@Composable
fun BackButton(modifier: Modifier = Modifier, onClick:()-> Unit,) = RotatedRoundedButton(
    onClick = onClick,
    resourceId = R.drawable.ic_chevron_left,
    contentDescription = "Back button",
    modifier = modifier
)