package com.synaptic.xcorevpn.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synaptic.xcorevpn.ui.components.FlatColoredButton
import com.synaptic.xcorevpn.ui.components.TextButton
import com.synaptic.xcorevpn.ui.screens.login.components.EnteringTextField
import com.synaptic.xcorevpn.ui.screens.login.components.LoginHugeText
import com.synaptic.xcorevpn.ui.theme.LightColors

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

@Composable
fun LoginScreen(){
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Scaffold{ innerPadding ->
        Column(modifier = Modifier
            .background(brush = LightColors.loginBGGradient)
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(193.dp))
            LoginHugeText(text = "Sign-up and protect\nyourself with our app")
            EnteringTextField(
                modifier = Modifier.padding(top = 30.dp, bottom = 100.dp),
                textValue = textState.value,
                errorState = LoginTextFieldError.Ok,
                hint = "Enter your e-mail",
                onChange = {}
            )
            FlatColoredButton(
                title = "Enter",
                enabled = true,
                onClick = {}
            )
            TextButton(title = "Can't get in?", onClick = {})
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}