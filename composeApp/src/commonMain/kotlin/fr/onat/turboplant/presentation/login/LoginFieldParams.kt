package fr.onat.turboplant.presentation.login

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.jetbrains.compose.resources.StringResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.email
import turboplant.composeapp.generated.resources.enter_your_email
import turboplant.composeapp.generated.resources.enter_your_password
import turboplant.composeapp.generated.resources.password


sealed class LoginFieldParams(
    val label: StringResource,
    val placeHolder: StringResource,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    data object Email : LoginFieldParams(
        label = Res.string.email,
        placeHolder = Res.string.enter_your_email,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            autoCorrectEnabled = false
        ),
        visualTransformation = VisualTransformation.None
    )

    data object Password : LoginFieldParams(
        label = Res.string.password,
        placeHolder = Res.string.enter_your_password,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            autoCorrectEnabled = false
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}