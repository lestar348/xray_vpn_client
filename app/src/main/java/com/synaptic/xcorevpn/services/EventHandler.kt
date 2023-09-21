package com.synaptic.xcorevpn.services

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.synaptic.xcorevpn.AppConstants
import com.synaptic.xcorevpn.models.ConfigEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventHandler {
    fun postEvent(event: ConfigEvent) {
        GlobalScope.launch(Dispatchers.Default) {
            toEventBus(event)
        }
    }

    private suspend inline fun toEventBus(configEvent: ConfigEvent) {
        EventBus.publish(configEvent)
    }

    fun subscribeConfigEvent(lifecycleOwner: LifecycleOwner, onHandle: (ConfigEvent) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            EventBus.subscribe<ConfigEvent> { event ->

                onHandle(event)
                Log.d(AppConstants.ANG_PACKAGE, "NativeBUS: $event ")
            }
        }
    }
}