package test.android.routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset

@Composable
fun RoutesAnimations(
    modifier: Modifier,
    route: String,
    content: @Composable BoxScope.(Routes) -> Unit,
    foreground: @Composable AnimatedVisibilityScope.(Routes) -> Unit,
) {
    val enter: EnterTransition = fadeIn(animationSpec = tween(durationMillis = 250, easing = LinearEasing))
    val exit: ExitTransition = fadeOut(animationSpec = tween(durationMillis = 250, easing = LinearEasing))
    val routes: Routes = LocalRoutes.current
    val state = routes.states.collectAsState().value
    AnimatedVisibility(
        modifier = modifier,
        visible = state.stack.contains(route),
        enter = enter,
        exit = exit,
    ) {
        val width: Int = LocalWindowInfo.current.containerSize.width
        val animationSpec: AnimationSpec<Int> = tween(durationMillis = 250, easing = FastOutSlowInEasing)
        val animatable = remember {
            val initialX: Int = if (route == routes.states.value.stack.firstOrNull()) 0 else width
            Animatable(initialX, Int.VectorConverter, null, route)
        }
        LaunchedEffect(route == state.stack.lastOrNull()) {
            val stack = routes.states.value.stack
            val value = when {
                !stack.contains(route) -> width
                route == stack.lastOrNull() -> 0
                else -> -width
            }
            animatable.animateTo(value, animationSpec)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(x = animatable.value, y = 0) },
            content = {
                content(routes)
            },
        )
        foreground(routes)
    }
}
