package com.synaptic.xcorevpn.ui.screens.login

import androidx.lifecycle.ViewModel
import com.synaptic.xcorevpn.services.navigation.AppRouts
import com.synaptic.xcorevpn.services.navigation.NavigationService
import com.synaptic.xcorevpn.services.network.implemantation.ApiImpl
import com.synaptic.xcorevpn.services.network.models.ConfirmEmailReq
import com.synaptic.xcorevpn.util.DeviceUtils
import com.synaptic.xcorevpn.util.MmkvManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConfirmEmailViewModel: ViewModel() {
    private val _validationState = MutableStateFlow(LoginTextFieldError.Ok)
    private val _loading = MutableStateFlow(false)
    private val api = ApiImpl()

    val validationState: StateFlow<LoginTextFieldError> = _validationState.asStateFlow()
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    val email = MmkvManager.getUserEmail()
    fun confirmEmail(code: String){
        if(_loading.value){ return }
        if(code.isEmpty() || code.length != 6){
            _validationState.value = LoginTextFieldError.IncorrectCode
            return
        }

        _loading.value = true
        api.confirmEmail(data = ConfirmEmailReq(deviceId = DeviceUtils.deviceID, emailCode = code), responseHandler = {
            _loading.value = false
            if(!it.isEmpty){

                MmkvManager.saveTGData(it)
                MmkvManager.saveUserID(it.user!!)

                NavigationService.shared.navigateTo(AppRouts.Home)
            }else{
                _validationState.value = LoginTextFieldError.IncorrectCode
            }
        })
    }
}