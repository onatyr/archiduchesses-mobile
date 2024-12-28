package fr.onat.turboplant.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import turboplant.composeapp.generated.resources.Judson_Bold
import turboplant.composeapp.generated.resources.Judson_Italic
import turboplant.composeapp.generated.resources.Judson_Regular
import turboplant.composeapp.generated.resources.Res

@Composable
fun JudsonFontFamily() = FontFamily(
    Font(Res.font.Judson_Bold, weight = FontWeight.Bold),
    Font(Res.font.Judson_Regular, weight = FontWeight.Normal),
    Font(Res.font.Judson_Italic, weight = FontWeight.Normal, style = FontStyle.Italic),
)