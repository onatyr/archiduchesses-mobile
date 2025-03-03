package fr.onat.turboplant.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.onat.turboplant.resources.Colors

@Composable
fun <T> SelectField(
    isExpended: MutableState<Boolean> = remember { mutableStateOf(false) },
    selectableOptions: List<T>,
    onSelectIndexed: (Int) -> Unit,
    content: @Composable () -> Unit,
    dropdownContent: (@Composable (T) -> Unit)? = null,
) {
    Box {
        Box(Modifier.clickable { isExpended.value = !isExpended.value }) {
            content()
        }
        DropdownMenu(
            modifier = Modifier.background(Colors.BlackGround),
            expanded = isExpended.value,
            onDismissRequest = { isExpended.value = false },
        ) {
            selectableOptions.forEachIndexed { index, option ->
                if (dropdownContent != null)
                    Box(
                        modifier = Modifier
                            .background(Colors.BlackGround)
                            .wrapContentSize()
                            .clickable {
                                onSelectIndexed(index); isExpended.value = !isExpended.value
                            }
                    ) {
                        dropdownContent(option)
                    }
                else
                    DropdownMenuItem(
                        modifier = Modifier.background(Colors.BlackGround).wrapContentSize(),
                        contentPadding = PaddingValues(0.dp),
                        content = { Text(option.toString()) },
                        onClick = { onSelectIndexed(index); isExpended.value = !isExpended.value }
                    )
            }
        }
    }
}