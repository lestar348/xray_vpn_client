package com.synaptic.xcorevpn.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.R
import com.synaptic.xcorevpn.ui.screens.home.HomeScreen
import com.synaptic.xcorevpn.ui.theme.LightColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar()= CenterAlignedTopAppBar(
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = LightColors.bgFrameLinearGradient1Color,
        titleContentColor = LightColors.bgFrameLinearGradient1Color,
    ),
    title = { Row{
    Image(
        painter = painterResource(
            id = R.drawable.appbar_app_icon
        ),
        contentDescription = "app icon",
        contentScale = ContentScale.None,
        modifier = Modifier.padding(end = 10.dp)
    )

    Image(
        painter = painterResource(
            id = R.drawable.app_logo
        ),
        contentDescription = "app logo",
        contentScale = ContentScale.None,
        modifier = Modifier.padding(end = 10.dp)
    )
}}, navigationIcon = {
    IconButton(onClick = { /* do something */ }) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Side Menu",
            tint = Color.White
        )
    }
}
)


@Preview
@Composable
private fun HomeAppBarPreview() {
    HomeAppBar()
}