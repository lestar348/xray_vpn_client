package com.synaptic.xcorevpn

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.synaptic.xcorevpn.models.ConfigEvent
import com.synaptic.xcorevpn.services.ConfigurationParser
import com.synaptic.xcorevpn.services.EventHandler
import com.synaptic.xcorevpn.ui.theme.XcoreVPNTheme
import com.tencent.mmkv.MMKV


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MMKV.initialize(this)
        MainActivity.context = applicationContext
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

    companion object {
        lateinit var context: Context
    }
}