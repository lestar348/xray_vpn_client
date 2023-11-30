package com.synaptic.vpn_core_lib.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.synaptic.vpn_core_lib.ConfigurationConstants
import com.synaptic.vpn_core_lib.core.XRayServiceManager
import com.synaptic.vpn_core_lib.setup.MmkvManager
import com.synaptic.vpn_core_lib.utils.Utils
import com.tencent.mmkv.MMKV

class TaskerReceiver : BroadcastReceiver() {
    private val mainStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_MAIN, MMKV.MULTI_PROCESS_MODE) }

    override fun onReceive(context: Context, intent: Intent?) {

        try {
            val bundle = intent?.getBundleExtra(ConfigurationConstants.TASKER_EXTRA_BUNDLE)
            val switch = bundle?.getBoolean(ConfigurationConstants.TASKER_EXTRA_BUNDLE_SWITCH, false)
            val guid = bundle?.getString(ConfigurationConstants.TASKER_EXTRA_BUNDLE_GUID, "")

            if (switch == null || guid == null || TextUtils.isEmpty(guid)) {
                return
            } else if (switch) {
                if (guid == ConfigurationConstants.TASKER_DEFAULT_GUID) {
                    Utils.startVServiceFromToggle(context)
                } else {
                    mainStorage?.encode(MmkvManager.KEY_SELECTED_SERVER, guid)
                    XRayServiceManager.startV2Ray(context)
                }
            } else {
                Utils.stopVService(context)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
