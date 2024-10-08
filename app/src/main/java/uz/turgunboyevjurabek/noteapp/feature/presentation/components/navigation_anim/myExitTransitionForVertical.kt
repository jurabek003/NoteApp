package uz.turgunboyevjurabek.noteapp.feature.presentation.components.navigation_anim

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically

fun myExitTransitionForVertical(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { fullHeight -> -fullHeight },
        animationSpec = tween(600)
    ) + fadeOut(animationSpec = tween(600))

}