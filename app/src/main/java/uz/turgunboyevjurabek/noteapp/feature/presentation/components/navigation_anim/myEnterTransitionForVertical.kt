package uz.turgunboyevjurabek.noteapp.feature.presentation.components.navigation_anim

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically

fun myEnterTransitionForVertical(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { fullHeight -> +fullHeight },
        animationSpec = tween(durationMillis = 600)
    )

}