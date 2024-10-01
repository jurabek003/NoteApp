package uz.turgunboyevjurabek.noteapp.feature.presentation.components.navigation_anim

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable


fun myExitTransition():ExitTransition {
   return slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(500)
    )  + fadeOut(animationSpec = tween(500))
}