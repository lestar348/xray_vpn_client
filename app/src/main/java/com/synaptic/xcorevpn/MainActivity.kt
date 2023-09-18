package com.synaptic.xcorevpn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.synaptic.xcorevpn.services.ConfigurationParser
import com.synaptic.xcorevpn.ui.theme.XcoreVPNTheme
import com.tencent.mmkv.MMKV


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MMKV.initialize(this)
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
            ConfigurationParser.getConfiguration(data)
        }

    }
}