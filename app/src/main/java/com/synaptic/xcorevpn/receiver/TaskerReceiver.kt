//package com.synaptic.xcorevpn.receiver
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.text.TextUtils
//import com.synaptic.xcorevpn.AppConstants
//import com.synaptic.xcorevpn.services.XRayServiceManager
//import com.synaptic.xcorevpn.util.MmkvManager
//import com.synaptic.xcorevpn.util.Utils
//import com.tencent.mmkv.MMKV
//
//class TaskerReceiver : BroadcastReceiver() {
//    private val mainStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_MAIN, MMKV.MULTI_PROCESS_MODE) }
//
//    override fun onReceive(context: Context, intent: Intent?) {
//
//        try {
//            val bundle = intent?.getBundleExtra(AppConstants.TASKER_EXTRA_BUNDLE)
//            val switch = bundle?.getBoolean(AppConstants.TASKER_EXTRA_BUNDLE_SWITCH, false)
//            val guid = bundle?.getString(AppConstants.TASKER_EXTRA_BUNDLE_GUID, "")
//
//            if (switch == null || guid == null || TextUtils.isEmpty(guid)) {
//                return
//            } else if (switch) {
//                if (guid == AppConstants.TASKER_DEFAULT_GUID) {
//                    Utils.startVServiceFromToggle(context)
//                } else {
//                    mainStorage?.encode(MmkvManager.KEY_SELECTED_SERVER, guid)
//                    XRayServiceManager.startV2Ray(context)
//                }
//            } else {
//                Utils.stopVService(context)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}
