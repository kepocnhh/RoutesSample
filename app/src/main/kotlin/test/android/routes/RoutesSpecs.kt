package test.android.routes

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.staticCompositionLocalOf

class RoutesSpecs(
    val x: AnimationSpec<Int>,
)

val LocalRoutesSpecs = staticCompositionLocalOf<RoutesSpecs> { error("No routes specs!") }
