package fr.onat.turboplant.presentation.plants.imagePicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher

@Composable
fun ImagePickerScreen() {
    val scope = rememberCoroutineScope()

//    val imagePicker = rememberImagePickerLauncher(
//        selectionMode = SelectionMode.Multiple(maxSelection = 1),
//        scope = scope,
//        onResult = { byteArrays ->
//            byteArrays.forEach {
//                println(it)
//            }
//        }
//    )
    Box(Modifier.fillMaxSize()) {
        Text("ImagePickerScreen")
    }
}