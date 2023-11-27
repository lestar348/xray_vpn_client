package com.synaptic.xcorevpn.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.services.navigation.AppRouts
import com.synaptic.xcorevpn.services.navigation.NavigationService
import com.synaptic.xcorevpn.services.network.implemantation.ApiImpl
import com.synaptic.xcorevpn.services.network.models.RegUserReq
import com.synaptic.xcorevpn.util.DeviceUtils
import com.synaptic.xcorevpn.util.MmkvManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class LoginTextFieldError{

    Ok, IncorrectEmail, EmailNotFound, IncorrectCode;
    val description: String
        get() =  when(this){
            Ok ->  "";
            IncorrectEmail->"*Incorrect email";
            EmailNotFound->"*Email not found in database";
            IncorrectCode->"*Incorrect code";
        }
}
class LoginViewModel: ViewModel() {

    private val _validationState = MutableStateFlow(LoginTextFieldError.Ok)
    private val _loading = MutableStateFlow(false)
    private val api = ApiImpl()

    val email = mutableStateOf(TextFieldValue())
    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    val validationState: StateFlow<LoginTextFieldError> = _validationState.asStateFlow()

    fun regUser(){
        if(_loading.value) { return }
        if(email.value.text.isEmpty() || !isValidEmail()){
            _validationState.value = LoginTextFieldError.IncorrectEmail
            return
        }
        _loading.value = true
        _validationState.value = LoginTextFieldError.Ok
        api.regUser(RegUserReq(deviceId = DeviceUtils.deviceID, email = email.value.text, deviceModel = DeviceUtils.model), responseHandler = { response ->

            MmkvManager.saveEmail(email.value.text)

            if(response.tgdata != null && !response.tgdata.isEmpty){

                MmkvManager.saveTGData(response.tgdata)
                MmkvManager.saveUserID(response.tgdata.user!!)

                _loading.value = false
                NavigationService.shared.navigateTo(AppRouts.Home)
            }else{
                _loading.value = false
                NavigationService.shared.navigateTo(AppRouts.ConfirmEmail)

            }

        })

    }

    private fun isValidEmail(): Boolean{
        // TODO add email regex check
        return true
    }
}