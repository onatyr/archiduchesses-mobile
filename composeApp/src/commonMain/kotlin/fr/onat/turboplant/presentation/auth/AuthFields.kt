package fr.onat.turboplant.presentation.auth

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import fr.onat.turboplant.presentation.composables.BaseTextField
import org.jetbrains.compose.resources.StringResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.email_placeholder
import turboplant.composeapp.generated.resources.enter_your_email
import turboplant.composeapp.generated.resources.enter_your_name
import turboplant.composeapp.generated.resources.enter_your_password
import turboplant.composeapp.generated.resources.name_placeholder
import turboplant.composeapp.generated.resources.password_placeholder

@Composable
fun NameField(
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    updateValue: (String) -> Unit
) {
    BaseTextField(
        labelRes = Res.string.enter_your_name,
        placeHolderRes = Res.string.name_placeholder,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction,
            autoCorrectEnabled = true
        ),
        value = value,
        updateValue = updateValue
    )
}

@Composable
fun EmailField(
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    updateValue: (String) -> Unit
) {
    BaseTextField(
        labelRes = Res.string.enter_your_email,
        placeHolderRes = Res.string.email_placeholder,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Email,
            imeAction = imeAction,
            autoCorrectEnabled = false
        ),
        value = value,
        updateValue = updateValue
    )
}

@Composable
fun PasswordField(
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    labelRes: StringResource = Res.string.enter_your_password,
    updateValue: (String) -> Unit
) {
    BaseTextField(
        labelRes = labelRes,
        placeHolderRes = Res.string.password_placeholder,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
            autoCorrectEnabled = false
        ),
        visualTransformation = PasswordVisualTransformation(),
        value = value,
        updateValue = updateValue
    )
}