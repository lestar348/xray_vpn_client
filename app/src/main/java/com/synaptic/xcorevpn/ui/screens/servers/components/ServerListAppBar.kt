package com.synaptic.xcorevpn.ui.screens.servers.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.ui.components.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerListAppBar(onClick: () -> Unit) {

    CenterAlignedTopAppBar(
        modifier = Modifier.heightIn(min = 80.dp, max = 80.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        title = {
            Text(
                "Your servers",
                maxLines = 1,
            )
        },
        navigationIcon = {
            BackButton(
                onClick = onClick, modifier = Modifier
                    .heightIn(min = 60.dp, max = 60.dp)
                    .widthIn(min = 60.dp, max = 60.dp)
            )
        },
        actions = {

        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
    )
}