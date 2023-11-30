package com.synaptic.xcorevpn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.synaptic.vpn_core_lib.VpnCorePlugin
import com.synaptic.xcorevpn.models.ConfigEvent
import com.synaptic.xcorevpn.services.EventHandler
import com.synaptic.xcorevpn.services.ConfigurationParser
import com.synaptic.xcorevpn.ui.theme.XcoreVPNTheme
import com.tencent.mmkv.MMKV


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MMKV.initialize(this)
        val pl = VpnCorePlugin(this, MainActivity::class)
        setContent {
            XcoreVPNTheme {
                XcoreApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val data = intent.data
        if(data != null){
            EventHandler().postEvent(ConfigEvent.ParsingConfig)
            ConfigurationParser.getConfiguration(data)
        }

    }
}