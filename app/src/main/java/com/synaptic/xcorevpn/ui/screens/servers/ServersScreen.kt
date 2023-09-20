package com.synaptic.xcorevpn.ui.screens.servers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.synaptic.xcorevpn.ui.screens.servers.components.ServerElement
import com.synaptic.xcorevpn.ui.screens.servers.components.ServerListAppBar


@Composable
fun ServerScreen(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ServerViewModel = viewModel(),
) {

    val selectedServer by viewModel.selectedServerUUID.collectAsState()

    Scaffold (
        topBar = {ServerListAppBar(onClick = onBackButtonClick)}
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                innerPadding
            )
        ) {
            viewModel.serverList.map { it ->
                if (it != null)
                    ServerElement(
                        server = it,
                        onClick = viewModel::onServerTap,
                        selected = it.subscriptionId == selectedServer,

                    )
            }
        }
    }


}


@Preview
@Composable
fun StartOrderPreview() {
    ServerScreen(
        onBackButtonClick = {}
    )
}