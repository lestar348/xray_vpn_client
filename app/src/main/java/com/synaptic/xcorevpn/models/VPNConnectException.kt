package com.synaptic.xcorevpn.models

enum class ConnectProblem{
    NeedAcceptPermission,
    InternalError
}
class VPNConnectException(message: String, val problem: ConnectProblem): Exception(message)