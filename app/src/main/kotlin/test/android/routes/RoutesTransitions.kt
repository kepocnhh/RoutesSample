package test.android.routes

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.staticCompositionLocalOf

class RoutesTransitions(
    val enter: EnterTransition,
    val exit: ExitTransition,
)

val LocalRoutesTransitions = staticCompositionLocalOf<RoutesTransitions> { error("No routes transitions!") }
