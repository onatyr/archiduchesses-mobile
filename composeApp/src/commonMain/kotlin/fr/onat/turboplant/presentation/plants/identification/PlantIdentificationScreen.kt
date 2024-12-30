package fr.onat.turboplant.presentation.plants.identification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import fr.onat.turboplant.libs.utils.toImageBitmap
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import org.koin.compose.viewmodel.koinViewModel

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

    Box {
        PeekabooCamera(
            state = cameraState,
            modifier = modifier.fillMaxSize(),
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