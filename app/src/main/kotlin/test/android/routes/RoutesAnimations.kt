package test.android.routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
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
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset

@Composable
fun RoutesAnimations(
    modifier: Modifier,
    state: RoutesState,
    route: String,
    transitions: RoutesTransitions,
    content: @Composable AnimatedVisibilityScope.(route: String) -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = state.stack.contains(route),
        enter = transitions.enter,
        exit = transitions.exit,
        content = {
            content(route)
        },
    )
}

@Composable
fun <T : Comparable<T>> Animatable<T, AnimationVector1D>.animate(
    animationSpec: AnimationSpec<T>,
    whenAnimate: () -> Any,
    targetValue: () -> T,
): T {
    LaunchedEffect(whenAnimate()) {
        animateTo(targetValue(), animationSpec)
    }
    return value
}

@Composable
fun Animatable<IntOffset, AnimationVector1D>.animateOffset(
    windowInfo: WindowInfo,
    animationSpec: AnimationSpec<IntOffset>,
    whenAnimate: () -> Any,
    targetValue: (WindowInfo) -> IntOffset,
): IntOffset {
    LaunchedEffect(whenAnimate()) {
        animateTo(targetValue(windowInfo), animationSpec)
    }
    return value
}

@Composable
fun animateXOffset(
    route: String,
    routes: Routes = LocalRoutes.current,
    windowInfo: WindowInfo = LocalWindowInfo.current,
    initialValue: Int = windowInfo.containerSize.width,
    animationSpec: AnimationSpec<Int> = tween(durationMillis = 250, easing = FastOutSlowInEasing),
): Int {
    val state = routes.states.collectAsState().value
    val animatable = remember {
        Animatable(initialValue, Int.VectorConverter, null, route)
    }
    LaunchedEffect(route == state.stack.lastOrNull()) {
        val width = windowInfo.containerSize.width
        val stack = routes.states.value.stack
        val value = when {
            !stack.contains(route) -> width
            route == stack.lastOrNull() -> 0
            else -> -width
        }
        animatable.animateTo(value, animationSpec)
    }
    return animatable.value
}

//@Composable
//fun animateXOffset(
//    route: String,
//    routes: Routes = LocalRoutes.current,
//    windowInfo: WindowInfo = LocalWindowInfo.current,
//    initialValue: Int = windowInfo.containerSize.width,
//    animationSpec: AnimationSpec<Int> = tween(durationMillis = 250, easing = FastOutSlowInEasing),
//): Int {
//    val state = routes.states.collectAsState().value
//    val width = windowInfo.containerSize.width
//    val animatable = remember {
//        Animatable(initialValue, Int.VectorConverter, null, route)
//    }
//    LaunchedEffect(state.stack.isEmpty()) {
//        val value = if (routes.states.value.stack.isEmpty()) 0 else -width
//        animatable.animateTo(value, animationSpec)
//    }
//    return animatable.value
//}

@Composable
fun animateXOffset(
    windowInfo: WindowInfo,
    animationSpec: AnimationSpec<Int>,
    initialValue: Int,
    label: String,
    whenAnimate: () -> Any,
    targetValue: (WindowInfo) -> Int,
): (Density.() -> IntOffset) {
    val animatable = remember {
        Animatable(initialValue, Int.VectorConverter, null, label)
    }
    LaunchedEffect(whenAnimate()) {
        animatable.animateTo(targetValue(windowInfo), animationSpec)
    }
    return { IntOffset(x = animatable.value, y = 0) }
}

fun Modifier.animateXOffset(
    animationSpec: AnimationSpec<Int>,
    initialValue: () -> Int,
    label: String,
    whenAnimate: () -> Any,
    targetValue: () -> Int,
): Modifier {
    return composed {
        val animatable = remember {
            Animatable(initialValue(), Int.VectorConverter, null, label)
        }
        LaunchedEffect(whenAnimate()) {
            animatable.animateTo(targetValue(), animationSpec)
        }
        Modifier.offset { IntOffset(x = animatable.value, y = 0) }
    }
}
