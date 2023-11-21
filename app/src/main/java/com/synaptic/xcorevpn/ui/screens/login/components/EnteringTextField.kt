package com.synaptic.xcorevpn.ui.screens.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synaptic.xcorevpn.ui.screens.login.LoginTextFieldError
import com.synaptic.xcorevpn.ui.theme.LightColors


@Composable
fun EnteringTextField(modifier: Modifier = Modifier,
                      textValue: TextFieldValue,
                      keyboardType: KeyboardType = KeyboardType.Email,
                      errorState: LoginTextFieldError,
                      hint: String,
                      onChange:(TextFieldValue) -> Unit) =
    OutlinedTextField(
    value = textValue,
    shape = RoundedCornerShape(100.dp),
    singleLine = true,
    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
    colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.White),
    modifier = modifier
        .height(80.dp)
        .fillMaxWidth(),
    textStyle = TextStyle(color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
    placeholder = { Text(text = hint, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium ) },
    isError = errorState != LoginTextFieldError.Ok,
    supportingText = {
        if ( errorState != LoginTextFieldError.Ok) {
            Text(
                text = errorState.description,
                color = LightColors.error,
                fontSize = 12.sp, fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    },
    onValueChange = onChange
)


@Preview
@Composable
private fun EnteringTextFieldPreview() {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    EnteringTextField(textValue = textState.value, hint = "Enter your e-mail", errorState = LoginTextFieldError.IncorrectEmail, onChange = { })
}