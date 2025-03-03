package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.darkokoa.datetimewheelpicker.WheelDatePicker
import dev.darkokoa.datetimewheelpicker.core.WheelPickerDefaults
import dev.darkokoa.datetimewheelpicker.core.format.dateFormatter
import fr.onat.turboplant.data.models.dto.Sunlight
import fr.onat.turboplant.libs.extensions.formatToString
import fr.onat.turboplant.presentation.composables.BaseTextField
import fr.onat.turboplant.presentation.composables.SelectField
import fr.onat.turboplant.presentation.composables.SmoothGreyBox
import fr.onat.turboplant.resources.Colors
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
        }
    )
}

@Composable
fun DateField(
    value: String,
    updateValue: (String) -> Unit
) {
    val instant = Instant.parse(value)
    var showDialog by remember { mutableStateOf(false) }

    BaseTextField(
        modifier = Modifier.clickable {
            showDialog = true
        },
        placeHolderRes = Res.string.adoption_date,
        value = "Adopted on ${instant.formatToString()}",
        enabled = false
    )

    if (showDialog)
        DatePickerDialog(
            startDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()),
            updateDate = { updateValue(it.toString()) },
            onDismissRequest = { showDialog = false }
        )
}

@Composable
fun DatePickerDialog(
    startDate: LocalDateTime,
    updateDate: (Instant) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            Modifier
                .background(color = Colors.SalmonPink, shape = RoundedCornerShape(15))
                .wrapContentSize()
        ) {
            SmoothGreyBox {
                WheelDatePicker(
                    size = DpSize(320.dp, 200.dp),
                    startDate = LocalDate(
                        year = startDate.year,
                        monthNumber = startDate.monthNumber,
                        dayOfMonth = startDate.dayOfMonth
                    ),
                    dateFormatter = dateFormatter(),
                    rowCount = 5,
                    textColor = Color.White,
                    selectorProperties = WheelPickerDefaults.selectorProperties(
                        enabled = true,
                        shape = RoundedCornerShape(0.dp),
                        color = Color(0xFFf1faee).copy(alpha = 0.4f),
                        border = BorderStroke(2.dp, Colors.SupraGreen)
                    ),
                ) { snappedDateTime ->
                    updateDate(Instant.fromEpochSeconds(snappedDateTime.toEpochDays() * 3600 * 24L))
                }
            }
        }
    }
}