package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import fr.onat.turboplant.data.models.dto.Sunlight
import fr.onat.turboplant.presentation.composables.BaseTextField
import fr.onat.turboplant.presentation.composables.SelectField
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.adoption_date
import turboplant.composeapp.generated.resources.plant_name
import turboplant.composeapp.generated.resources.species
import turboplant.composeapp.generated.resources.sunlight
import turboplant.composeapp.generated.resources.watering_recurrence

@Composable
fun NameField(
    value: String,
    updateValue: (String) -> Unit
) {
    BaseTextField(
        placeHolderRes = Res.string.plant_name,
        value = value,
        updateValue = updateValue
    )
}

@Composable
fun SpeciesField(
    value: String,
    updateValue: (String) -> Unit
) {
    BaseTextField(
        placeHolderRes = Res.string.species,
        value = value,
        updateValue = updateValue
    )
}

@Composable
fun WateringRecurrenceField(
    value: String,
    updateValue: (String) -> Unit
) {
    BaseTextField(
        placeHolderRes = Res.string.watering_recurrence,
        value = value,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
        updateValue = updateValue
    )
}

@Composable
fun SunlightField(
    value: String,
    updateValue: (Int) -> Unit
) {
    SelectField(
        selectableOptions = Sunlight.entries.map { it.textValue },
        onSelectIndexed = updateValue,
        content = {
            BaseTextField(
                value = value,
                placeHolderRes = Res.string.sunlight,
                enabled = false
            )
        },
        dropdownContent = { Text(text = it, color = Color.Black) }
    )
}

@Composable
fun DateField(
    value: String,
    updateValue: (String) -> Unit
) {
    BaseTextField(
        placeHolderRes = Res.string.adoption_date,
        value = value,
        updateValue = updateValue
    )
}

//Icon(
//painter = painterResource(Res.drawable.eye_scan_icon),
//contentDescription = null,
//tint = Color.White,
//modifier = Modifier
//.clip(RoundedCornerShape(15.dp))
//.clickable(
//interactionSource = remember { MutableInteractionSource() },
//indication = ripple(
//bounded = true,
//color = Color.White
//)
//) { onIconClick() }
//.align(Alignment.CenterVertically)
//.padding(20.dp)
//.size(100.dp)
//)