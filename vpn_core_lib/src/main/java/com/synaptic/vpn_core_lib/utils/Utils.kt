package com.synaptic.vpn_core_lib.utils

import android.content.Context
import android.util.Log
import com.synaptic.vpn_core_lib.ConfigurationConstants
import com.synaptic.vpn_core_lib.core.XRayServiceManager
import com.synaptic.vpn_core_lib.setup.MmkvManager
import com.tencent.mmkv.MMKV
import java.util.UUID

object Utils {

    private val settingsStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_SETTING, MMKV.MULTI_PROCESS_MODE) }
    private val mainStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_MAIN, MMKV.MULTI_PROCESS_MODE) }

    fun startVServiceFromToggle(context: Context): Boolean {
        if (mainStorage?.decodeString(MmkvManager.KEY_SELECTED_SERVER).isNullOrEmpty()) {
            Log.i(ConfigurationConstants.ANG_PACKAGE, "No SELECTED SERVER")
            return false
        }
        XRayServiceManager.startV2Ray(context)
        return true
    }

    fun stopVService(context: Context) {
        //context.toast(R.string.toast_services_stop)
        MessageUtil.sendMsg2Service(context, ConfigurationConstants.MSG_STATE_STOP, "")
    }

    fun userAssetPath(context: Context?): String {
        if (context == null)
            return ""
        val extDir = context.getExternalFilesDir(ConfigurationConstants.DIR_ASSETS)
            ?: return context.getDir(ConfigurationConstants.DIR_ASSETS, 0).absolutePath
        return extDir.absolutePath
    }

    /**
     * readTextFromAssets
     */
    fun readTextFromAssets(context: Context, fileName: String): String {
        val content = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        return content
    }

    fun parseInt(str: String?, default: Int): Int {
        str ?: return default
        return try {
            Integer.parseInt(str)
        } catch (e: Exception) {
            e.printStackTrace()
            default
        }
    }
    /**
     * is ip address
     */
    fun isIpAddress(value: String): Boolean {
        try {
            var addr = value
            if (addr.isEmpty() || addr.isBlank()) {
                return false
            }
            //CIDR
            if (addr.indexOf("/") > 0) {
                val arr = addr.split("/")
                if (arr.count() == 2 && Integer.parseInt(arr[1]) > -1) {
                    addr = arr[0]
                }
            }

            // "::ffff:192.168.173.22"
            // "[::ffff:192.168.173.22]:80"
            if (addr.startsWith("::ffff:") && '.' in addr) {
                addr = addr.drop(7)
            } else if (addr.startsWith("[::ffff:") && '.' in addr) {
                addr = addr.drop(8).replace("]", "")
            }

            // addr = addr.toLowerCase()
            val octets = addr.split('.').toTypedArray()
            if (octets.size == 4) {
                if (octets[3].indexOf(":") > 0) {
                    addr = addr.substring(0, addr.indexOf(":"))
                }
                return isIpv4Address(addr)
            }

            // Ipv6addr [2001:abc::123]:8080
            return isIpv6Address(addr)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun isIpv4Address(value: String): Boolean {
        val regV4 = Regex("^([01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])$")
        return regV4.matches(value)
    }

    fun isIpv6Address(value: String): Boolean {
        var addr = value
        if (addr.indexOf("[") == 0 && addr.lastIndexOf("]") > 0) {
            addr = addr.drop(1)
            addr = addr.dropLast(addr.count() - addr.lastIndexOf("]"))
        }
        val regV6 = Regex("^((?:[0-9A-Fa-f]{1,4}))?((?::[0-9A-Fa-f]{1,4}))*::((?:[0-9A-Fa-f]{1,4}))?((?::[0-9A-Fa-f]{1,4}))*|((?:[0-9A-Fa-f]{1,4}))((?::[0-9A-Fa-f]{1,4})){7}$")
        return regV6.matches(addr)
    }

    fun isPureIpAddress(value: String): Boolean {
        return (isIpv4Address(value) || isIpv6Address(value))
    }

    private fun isCoreDNSAddress(s: String): Boolean {
        return s.startsWith("https") || s.startsWith("tcp") || s.startsWith("quic")
    }

    /**
     * get remote dns servers from preference
     */
    fun getRemoteDnsServers(): List<String> {
        val remoteDns = settingsStorage?.decodeString(ConfigurationConstants.PREF_REMOTE_DNS) ?: ConfigurationConstants.DNS_AGENT
        val ret = remoteDns.split(",").filter { isPureIpAddress(it) || isCoreDNSAddress(it) }
        if (ret.isEmpty()) {
            return listOf(ConfigurationConstants.DNS_AGENT)
        }
        return ret
    }

    /**
     * get remote dns servers from preference
     */
    fun getDomesticDnsServers(): List<String> {
        val domesticDns = settingsStorage?.decodeString(ConfigurationConstants.PREF_DOMESTIC_DNS) ?: ConfigurationConstants.DNS_DIRECT
        val ret = domesticDns.split(",").filter { isPureIpAddress(it) || isCoreDNSAddress(it) }
        if (ret.isEmpty()) {
            return listOf(ConfigurationConstants.DNS_DIRECT)
        }
        return ret
    }

    fun getVpnDnsServers(): List<String> {
        val vpnDns = settingsStorage?.decodeString(ConfigurationConstants.PREF_VPN_DNS)
            ?: settingsStorage?.decodeString(ConfigurationConstants.PREF_REMOTE_DNS)
            ?: ConfigurationConstants.DNS_AGENT
        return vpnDns.split(",").filter { isPureIpAddress(it) }
        // allow empty, in that case dns will use system default
    }

    /**
     * uuid
     */
    fun getUuid(): String {
        return try {
            UUID.randomUUID().toString().replace("-", "")
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}