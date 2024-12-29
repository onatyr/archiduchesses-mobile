package fr.onat.turboplant.presentation.plant.newPlant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.onat.turboplant.data.dto.NewPlantDto
import fr.onat.turboplant.resources.JudsonFontFamily
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.eye_scan_icon
import turboplant.composeapp.generated.resources.plant_name
import turboplant.composeapp.generated.resources.species

@Composable
fun NewPlantCardHeader(
    newPlant: NewPlantDto,
    updateName: (String) -> Unit,
    updateSpecies: (String) -> Unit,
    onIconClick: () -> Unit
) {
    Row {
        Column(
            modifier = Modifier
                .align(Alignment.Top)
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            NewPlantTextField(
                value = newPlant.name ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                onValueChange = updateName,
                placeHolderText = stringResource(Res.string.plant_name)
            )
            NewPlantTextField(
                value = newPlant.species ?: "",
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp,
                onValueChange = updateSpecies,
                modifier = Modifier.padding(start = 10.dp),
                placeHolderText = stringResource(Res.string.species),
            )
        }
        Icon(
            painter = painterResource(Res.drawable.eye_scan_icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(
                        bounded = true,
                        color = Color.White
                    )
                ) { onIconClick() }
                .align(Alignment.CenterVertically)
                .padding(20.dp)
                .size(100.dp)
        )
    }
}

@Composable
fun NewPlantTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    placeHolderText: String
) {
    val textStyle = LocalTextStyle.current.copy(
        fontFamily = JudsonFontFamily(),
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
    )
    TextField(
        value = value,
        textStyle = textStyle,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            cursorColor = Color.White
        ),
        singleLine = true,
        placeholder = {
            Text(
                text = placeHolderText,
                style = textStyle
            )
        }
    )
}