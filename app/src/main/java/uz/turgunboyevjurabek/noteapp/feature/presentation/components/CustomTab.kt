package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun CustomTab(
    modifier: Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit
) {
    val rowShadowColor = if (isSystemInDarkTheme()) {
        if (selected) Color.Green else Color.White
    } else if (selected) Color.Black else Color.White

    // Custom tabni shakllantirish
    Surface(
        onClick = { onClick() },
        modifier = modifier
            .padding(10.dp)
            .graphicsLayer {
                spotShadowColor = rowShadowColor
                shadowElevation = if (selected) 30f else 10f
                shape = ShapeDefaults.Large
            },
        shape = ShapeDefaults.Large,
//        colors = CardDefaults.cardColors(if (selected) Color.Cyan else Color.White),
//        elevation = CardDefaults.cardElevation(10.dp)

    ) {
        Column(
            modifier = modifier
                .padding(5.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            text()
        }
    }
}