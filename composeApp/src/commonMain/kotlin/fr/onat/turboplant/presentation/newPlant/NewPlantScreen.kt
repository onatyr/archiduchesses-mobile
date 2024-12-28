package fr.onat.turboplant.presentation.newPlant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.ripple
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.data.dto.NewPlantDto
import fr.onat.turboplant.data.dto.NewPlantField
import fr.onat.turboplant.libs.extensions.toStringOrNull
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.composables.SelectField
import fr.onat.turboplant.presentation.plantList.PlantViewModel
import fr.onat.turboplant.resources.Colors
import fr.onat.turboplant.resources.JudsonFontFamily
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import turboplant.composeapp.generated.resources.Judson_Bold
import turboplant.composeapp.generated.resources.Judson_Italic
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.eye_scan_icon
import turboplant.composeapp.generated.resources.plant_name
import turboplant.composeapp.generated.resources.species

@Composable
fun NewPlantScreen(
    viewModel: PlantViewModel = koinViewModel(),
    navigate: (NavRoute) -> Unit
) {
    val newPlant by viewModel.newPlant.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()

    LaunchedEffect(newPlant.species) {
        newPlant.species?.let {
            if (it.length >= 3) viewModel.searchExternalPlantByName(it)
        }
    }

    Box(Modifier.padding(15.dp).background(Colors.PlantCardGreen, RoundedCornerShape(15.dp))) {
        Column(
            Modifier.padding(10.dp).fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(5.dp))
        ) {
            NewPlantCardHeader(
                newPlant = newPlant,
                updateName = { viewModel.updateNewPlant(NewPlantField.Name, it) },
                updateSpecies = { viewModel.updateNewPlant(NewPlantField.Species, it) },
                onIconClick = {}
            )
        }

    }
}

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
            NewPlantField(
                value = newPlant.name ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                onValueChange = updateName,
                placeHolderText = stringResource(Res.string.plant_name)
            )
            NewPlantField(
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
                ) { onIconClick }
                .align(Alignment.CenterVertically)
                .padding(20.dp)
                .size(100.dp)
        )
    }
}

@Composable
fun NewPlantField(
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

@Composable
fun BoxScope.ImageIdentificationButton(openCamera: () -> Unit) {
    Button(
        onClick = openCamera,
        modifier = Modifier.size(100.dp).align(Alignment.BottomCenter).padding(20.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.SalmonPink,
            contentColor = Color.Black
        )
    ) {
        Icon(Icons.Default.Create, "add new plant", Modifier.scale(2f))
    }
}