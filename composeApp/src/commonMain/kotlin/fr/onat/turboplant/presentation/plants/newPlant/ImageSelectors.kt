package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import fr.onat.turboplant.data.models.dto.NewPlantField
import fr.onat.turboplant.libs.extensions.toPx
import fr.onat.turboplant.libs.utils.LocalBottomPadding
import fr.onat.turboplant.libs.utils.getScreenSize
import fr.onat.turboplant.libs.utils.toImageBitmap
import fr.onat.turboplant.presentation.composables.SmoothGreyBox
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import fr.onat.turboplant.resources.Colors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.add_image_icon
import turboplant.composeapp.generated.resources.eye_scan_icon

@Composable
fun ImageSelectorsRow(
    selectedImageByteArray: ByteArray?,
    clearIdentificationResult: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageSelector(
            backgroundImage = { modifier ->
                selectedImageByteArray?.let {
                    Image(
                        modifier = modifier,
                        painter = BitmapPainter(selectedImageByteArray.toImageBitmap()),
                        contentDescription = "Selected image",
                        contentScale = ContentScale.FillBounds
                    )
                }
            },
            iconRes = Res.drawable.add_image_icon,
            subText = "Add a picture",
            selector = { _ -> ImagePickerScreen() },
            onDismissRequest = { }
        )
        Spacer(Modifier.size(50.dp))
        ImageSelector(
            iconRes = Res.drawable.eye_scan_icon,
            subText = "Identify a plant",
            selector = { modifier ->
                PlantIdentificationSelector(modifier)
            },
            onDismissRequest = clearIdentificationResult
        )
    }
}

@Composable
fun ImageSelector(
    selector: @Composable (Modifier) -> Unit,
    subText: String,
    backgroundImage: @Composable (Modifier) -> Unit = {},
    iconRes: DrawableResource,
    onDismissRequest: () -> Unit,
) {
    var showPopup by remember { mutableStateOf(false) }
    SmoothGreyBox(
        backgroundImage = { modifier -> backgroundImage(modifier) },
        modifier = Modifier
            .size(120.dp)
            .clickable(
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
                .padding(10.dp)
        )
        Text(text = subText)
    }
    if (showPopup) {
        val screenSize = getScreenSize()
        val (popupWidthSize, widthPadding) =
            screenSize.widthDp.dp * 0.8f to screenSize.widthDp.dp * 0.2f
        val popupHeightSize = screenSize.heightDp.dp * 0.8f - LocalBottomPadding.current

        Popup(
            onDismissRequest = {
                showPopup = false
                onDismissRequest()
            },
            offset = IntOffset(
                x = (widthPadding / 2).toPx(LocalDensity.current).toInt(),
                y = -960
            )
        ) {
            Box(
                Modifier
                    .background(color = Colors.SalmonPink)
                    .padding(5.dp)
                    .wrapContentSize()
            ) {
                selector(
                    Modifier
                        .width(popupWidthSize)
                        .height(popupHeightSize)
                        .clip(RoundedCornerShape(2)),
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
    Box(Modifier.fillMaxSize().background(Color.Black)) {
        Text("ImagePickerScreen")
    }
}

@Composable
fun PlantIdentificationSelector(
    modifier: Modifier = Modifier,
    viewModel: PlantsViewModel = koinViewModel(),
) {

    var capturedImage by remember { mutableStateOf<ByteArray?>(null) }
    var captureLaunched by remember { mutableStateOf(false) }
    val cameraState = rememberPeekabooCameraState(onCapture = { capturedImage = it })

    val identificationResult by viewModel.identificationResult.collectAsStateWithLifecycle()

    LaunchedEffect(capturedImage) {
        capturedImage ?: return@LaunchedEffect
        viewModel.identify(
            image = capturedImage,
            onError = { capturedImage = null; captureLaunched = false })
    }

    Box {
        PeekabooCamera(
            state = cameraState,
            modifier = modifier,
        )
        if (!captureLaunched)
            Column(Modifier.align(Alignment.BottomCenter)) {
                Box(
                    modifier = Modifier
                        .background(Colors.TurboGreen.copy(alpha = 0.7f), RoundedCornerShape(15.dp))
                        .padding(4.dp)
                        .clickable {
                            captureLaunched = true
                            cameraState.capture()
                        }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.eye_scan_icon),
                        modifier = Modifier.size(60.dp),
                        contentDescription = "Button to scan the plant",
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                }
                Spacer(Modifier.size(15.dp))
            }
    }

    AnimatedVisibility(
        visible = captureLaunched,
        enter = slideInVertically(initialOffsetY = { it / 2 }),
        exit = slideOutVertically(targetOffsetY = { it / 2 })
    ) {
        Box(modifier.background(Color.Black)) {
            if (identificationResult == null)
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Identification in progress",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Left
                    )
                    Spacer(Modifier.size(15.dp))
                    CircularProgressIndicator(color = Colors.SalmonPink)
                }
            else
                LazyColumn {
                    items(identificationResult ?: emptyList()) { plant ->
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .background(
                                    Color.Black, RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    viewModel.uploadImage(
                                        image = capturedImage,
                                        onSuccess = {
                                            plant.plantbookDetailsDto?.displayPid?.let { species ->
                                                viewModel.updateNewPlant(
                                                    NewPlantField.Species,
                                                    species
                                                )
                                            }
                                        })
                                }
                        ) {
                            AsyncImage(
                                model = plant.plantbookDetailsDto?.imageUrl,
                                contentDescription = "Image representing a ${plant.plantnetName}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(Modifier.size(10.dp))
                            Column(verticalArrangement = Arrangement.Center) {
                                Text(text = plant.plantnetName, fontSize = 20.sp)
                                Spacer(Modifier.size(10.dp))
                                Text(text = "Score: ${plant.score}")
                            }
                        }
                    }
                }
        }
    }
}