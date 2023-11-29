package com.synaptic.vpn_core_lib.models

enum class ConnectProblem{
    NeedAcceptPermission,
    InternalError
}
class VPNConnectException(message: String, val problem: ConnectProblem): Exception(message){
    companion object{

        val needPermission = VPNConnectException(message = "Need accept configuration", problem = ConnectProblem.NeedAcceptPermission)

        val internalError = VPNConnectException(message = "Internal Error", problem = ConnectProblem.InternalError)
    }
}