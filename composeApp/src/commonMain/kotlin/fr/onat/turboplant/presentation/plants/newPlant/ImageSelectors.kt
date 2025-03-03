package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import fr.onat.turboplant.libs.utils.LocalBottomPadding
import fr.onat.turboplant.libs.utils.toImageBitmap
import fr.onat.turboplant.presentation.composables.SmoothGreyBox
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.add_image_icon
import turboplant.composeapp.generated.resources.eye_scan_icon

@Composable
fun ImageSelectorsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageSelector(
            iconRes = Res.drawable.add_image_icon,
            subText = "Add a picture",
            selector = { ImagePickerScreen() }
        )
        Spacer(Modifier.size(50.dp))
        ImageSelector(
            iconRes = Res.drawable.eye_scan_icon,
            subText = "Identify a plant",
            selector = { modifier -> PlantIdentificationScreen(modifier) }
        )
    }
}

@Composable
fun ImageSelector(
    selector: @Composable (Modifier) -> Unit,
    subText: String,
    iconRes: DrawableResource
) {
    var showPopup by remember { mutableStateOf(false) }
    SmoothGreyBox(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(
                bounded = true,
                color = Color.White
            ),
            onClick = { showPopup = true }
        )
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        )
        Spacer(Modifier.size(5.dp))
        Text(subText)
    }
    if (showPopup) {
        Popup(onDismissRequest = { showPopup = false }) {
            Box(
                Modifier
                    .border(2.dp, Color.Red)
                    .fillMaxSize()
                    .padding(
                        start = 10.dp,
                        top = 10.dp,
                        end = 10.dp,
                        bottom = LocalBottomPadding.current + 10.dp
                    )
            ) {
                selector(
                    Modifier.fillMaxSize()
                )
            }
        }
    }
}

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

@Composable
fun PlantIdentificationScreen(
    modifier: Modifier = Modifier, viewModel: PlantsViewModel = koinViewModel(),
) {
    var capturedImage by remember { mutableStateOf<ByteArray?>(null) }
    var isCapturing by remember { mutableStateOf(false) }
    val cameraState = rememberPeekabooCameraState(onCapture = { capturedImage = it })

    val identificationResult by viewModel.identificationResult.collectAsStateWithLifecycle()

    LaunchedEffect(capturedImage) {
        capturedImage ?: return@LaunchedEffect
        viewModel.identify(
            image = capturedImage,
            onError = { capturedImage = null; isCapturing = false })
    }

    Box(modifier) {
        PeekabooCamera(
            state = cameraState,
            modifier = Modifier.fillMaxSize(),
        )
        if (isCapturing)
            Icon(Icons.Default.Lock, "") // todo change
        else
            Button(
                onClick = { cameraState.capture(); isCapturing = true },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Icon(Icons.Default.Add, "") // todo change
            }

        AnimatedVisibility(
            visible = capturedImage != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Image(
                bitmap = capturedImage.toImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
        }
    }

    AnimatedVisibility(
        visible = identificationResult?.isNotEmpty() ?: false,
        enter = slideInVertically(initialOffsetY = { it / 2 }),
        exit = slideOutVertically(targetOffsetY = { it / 2 })
    ) {
        LazyColumn(
            Modifier
                .padding(vertical = 10.dp)
                .background(Color.Black)
                .fillMaxSize()
        ) {
            items(identificationResult ?: emptyList()) { plant ->
                Text(plant.plantnetName)
            }
        }
    }
}