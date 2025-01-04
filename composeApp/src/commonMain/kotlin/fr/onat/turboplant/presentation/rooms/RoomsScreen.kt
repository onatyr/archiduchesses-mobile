package fr.onat.turboplant.presentation.rooms

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RoomsScreen(viewModel: RoomsViewModel = koinViewModel()) {
    val rooms by viewModel.rooms.collectAsStateWithLifecycle(emptyList())

    LazyColumn(Modifier.fillMaxSize()) {
        items(rooms, key = { it.id }) {
            Text(it.label)
        }
    }
}