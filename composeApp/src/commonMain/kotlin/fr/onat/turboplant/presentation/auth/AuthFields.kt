package fr.onat.turboplant.presentation.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import fr.onat.turboplant.resources.Colors
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.confirm_your_password
import turboplant.composeapp.generated.resources.email_placeholder
import turboplant.composeapp.generated.resources.enter_your_email
import turboplant.composeapp.generated.resources.enter_your_name
import turboplant.composeapp.generated.resources.enter_your_password
import turboplant.composeapp.generated.resources.name_placeholder
import turboplant.composeapp.generated.resources.password_placeholder

@Composable
private fun BaseAuthField(
    labelRes: StringResource,
    placeHolderRes: StringResource,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    value: String,
    updateValue: (String) -> Unit
) {
    Column(Modifier.padding(10.dp)) {
        Text(
            text = stringResource(labelRes),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()

        )
        Divider(thickness = 5.dp, color = Color.Transparent)
        TextField(
            value = value,
            onValueChange = updateValue,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Colors.BlackGround,
                textColor = LocalContentColor.current,
                focusedIndicatorColor = Colors.SalmonPink
            ),
            placeholder = {
                Text(stringResource(placeHolderRes), color = Color.White.copy(alpha = 0.5f))
            },
            modifier = Modifier.fillMaxWidth()
                .border(
                    2.dp,
                    LocalContentColor.current.copy(LocalContentAlpha.current),
                    RoundedCornerShape(5.dp)
                )
        )
    }
}

@Composable
fun NameField(
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    updateValue: (String) -> Unit
) {
    BaseAuthField(
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
    BaseAuthField(
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
    confirmField: Boolean = false,
    updateValue: (String) -> Unit
) {
    BaseAuthField(
        labelRes = if (confirmField) Res.string.confirm_your_password else Res.string.enter_your_password,
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