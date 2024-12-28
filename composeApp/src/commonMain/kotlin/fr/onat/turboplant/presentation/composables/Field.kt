package fr.onat.turboplant.presentation.composables

import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp

@Composable
fun SelectField(modifier: Modifier = Modifier) {
    var selectedValue by remember { mutableStateOf("SELECT FIELD TEST") }
    var isExpended by remember { mutableStateOf(false) }

    Box {
        Text(text = selectedValue, modifier = modifier.clickable { isExpended = !isExpended }.border(2.dp, Color.Red))
        DropdownMenu(
            expanded = isExpended,
            onDismissRequest = { isExpended = false }
        ) {
            DropdownMenuItem(
                content = { Text("Option 1") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                content = { Text("Option 2") },
                onClick = { /* Do something... */ }
            )
        }
    }
}