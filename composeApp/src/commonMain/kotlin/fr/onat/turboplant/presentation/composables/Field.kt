package fr.onat.turboplant.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fr.onat.turboplant.data.models.PlantbookDetailsDto
import fr.onat.turboplant.libs.logger.logger
import kotlin.math.log

@Composable
fun <T> SelectField(
    modifier: Modifier = Modifier,
    selectableOptions: List<T>,
    onSelectIndexed: (Int) -> Unit,
    content: @Composable () -> Unit,
    dropdownContent: @Composable (T) -> Unit,
) {
    var isExpended by remember { mutableStateOf(false) }
    logger("selectable", selectableOptions)
    logger("isExpended", isExpended)

    Box(modifier) {
        Box(Modifier.clickable { isExpended = !isExpended }) {
            content()
        }
        DropdownMenu(
            expanded = isExpended,
            onDismissRequest = { isExpended = false }
        ) {
            selectableOptions.forEachIndexed { index, option ->
                DropdownMenuItem(
                    content = { dropdownContent(option) },
                    onClick = { onSelectIndexed(index); isExpended = !isExpended }
                )
            }
        }
    }
}

