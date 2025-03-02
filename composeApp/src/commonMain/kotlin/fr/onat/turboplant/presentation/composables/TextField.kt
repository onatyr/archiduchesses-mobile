package fr.onat.turboplant.presentation.composables

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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import fr.onat.turboplant.resources.Colors
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BaseTextField(
    value: String,
    updateValue: (String) -> Unit = {},
    labelRes: StringResource? = null,
    placeHolderRes: StringResource,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
) {
    Column(Modifier.padding(10.dp)) {
        labelRes?.let {
            Text(
                text = stringResource(labelRes),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()

            )
            Divider(thickness = 5.dp, color = Color.Transparent)
        }
        TextField(
            value = value,
            onValueChange = updateValue,
            enabled = enabled,
            visualTransformation = visualTransformation,
            leadingIcon = leadingIcon,
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