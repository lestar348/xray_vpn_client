package com.synaptic.xcorevpn.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaptic.xcorevpn.ui.components.FlatColoredButton
import com.synaptic.xcorevpn.ui.components.TextButton
import com.synaptic.xcorevpn.ui.screens.login.components.ConfirmEmailTip
import com.synaptic.xcorevpn.ui.screens.login.components.EnteringTextField
import com.synaptic.xcorevpn.ui.screens.login.components.LoginHugeText
import com.synaptic.xcorevpn.ui.theme.LightColors

@Composable
fun ConfirmEmailScreen(viewModel: ConfirmEmailViewModel = ConfirmEmailViewModel()){
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val errorState by viewModel.validationState.collectAsState()

    Scaffold { it ->
        Column(
            modifier = Modifier
                .background(brush = LightColors.loginBGGradient)
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(184.dp))
            LoginHugeText(text = "Confirm your e-mail")

            EnteringTextField(
                modifier = Modifier.padding(top = 30.dp, bottom = 30.dp),
                textValue = textState.value,
                errorState = errorState,
                hint = "Enter code",
                onChange = {textValue ->
                    textState.value = textValue
                }
            )
            ConfirmEmailTip(viewModel.email!!)

            // TODO add resend button

            FlatColoredButton(
                modifier = Modifier.padding(top = 45.dp),
                title = "Enter",
                enabled = true,
                onClick = {
                    viewModel.confirmEmail(code = textState.value.text)
                }
            )
            TextButton(title = "Can't get in?", onClick = {})
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ConfirmEmailScreenPreview() {
    ConfirmEmailScreen()
}