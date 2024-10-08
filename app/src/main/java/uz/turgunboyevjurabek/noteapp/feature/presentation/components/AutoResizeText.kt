package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AutoResizingText(
    text: String,
    modifier: Modifier,
    style: TextStyle = TextStyle.Default,
    boxWidth: Double=1.0
) {
    BoxWithConstraints(modifier = modifier) {
        var textSize by remember { mutableStateOf(style.fontSize) }
        val textMeasurer = rememberTextMeasurer()
        var witdh by remember { mutableStateOf(constraints.maxWidth.toDouble()) }

        LaunchedEffect(constraints.maxWidth, text) {
            var shrink = textSize
            do {
                val measuredText = textMeasurer.measure(
                    text = AnnotatedString(text),
                    style = style.copy(fontSize = shrink)
                )
                if (measuredText.size.width > constraints.maxWidth) {
                    shrink *= 0.9 // Har bosqichda textni 10% kichraytirish
                } else break
            } while (true)

            textSize = shrink
        }

        Text(
            text = text,
            fontSize = textSize,
            modifier = modifier
                .fillMaxWidth(),
            style = style
        )
    }
}
