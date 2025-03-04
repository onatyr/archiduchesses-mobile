package fr.onat.turboplant.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fr.onat.turboplant.resources.Colors

@Composable
fun SmoothGreyBox(
    modifier: Modifier = Modifier,
    backgroundImage: @Composable (Modifier) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(
                color = Colors.SmoothGrey,
                shape = RoundedCornerShape(20)
            ).then(modifier),
        contentAlignment = Alignment.Center
    ) {
        backgroundImage(Modifier.fillMaxSize().clip(RoundedCornerShape(20)))
        Column(
            modifier = Modifier.padding(10.dp).matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}