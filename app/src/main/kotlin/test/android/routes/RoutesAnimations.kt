package test.android.routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import kotlin.time.Duration

@Composable
fun RoutesAnimations(
    modifier: Modifier,
    route: String,
    routes: Routes = LocalRoutes.current,
    spec: TweenSpec<Float>,
    content: @Composable RoutesAnimationsScope.() -> Unit,
) {
    val scopes = remember {
        val scope = RoutesAnimationsScope(
            route = route,
            currentValue = 0f,
        )
        mutableStateOf(scope)
    }
    // todo own animatable
    val animatable = remember {
        Animatable(0f, Float.VectorConverter, label = route)
    }
    val isVisible = routes.states.collectAsState().value.has(route = route)
    LaunchedEffect(isVisible) {
        val targetValue = if (routes.states.value.has(route = route)) 1f else 0f
        if (targetValue != animatable.value) {
            animatable.animateTo(targetValue, spec)
        }
    }
    LaunchedEffect(animatable.value) {
        scopes.value = scopes.value.copy(
            currentValue = animatable.value,
        )
    }
    if (isVisible || animatable.value > 0f) {
        Box(modifier = modifier) {
            scopes.value.content()
        }
    }
}

@Composable
fun RoutesAnimations(
    modifier: Modifier,
    route: String,
    routes: Routes = LocalRoutes.current,
    transitions: RoutesTransitions = LocalRoutesTransitions.current,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val state = routes.states.collectAsState().value
    // todo own visibility
    AnimatedVisibility(
        modifier = modifier,
        visible = state.has(route = route),
        enter = transitions.enter,
        exit = transitions.exit,
        content = content,
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

/*
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
*/

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

@Composable
fun animateXOffset(
    route: String,
    routes: Routes = LocalRoutes.current,
    windowInfo: WindowInfo = LocalWindowInfo.current,
    animationSpec: AnimationSpec<Int> = LocalRoutesSpecs.current.x,
    initialValue: Int = windowInfo.containerSize.width,
    label: String = route,
): (Density.() -> IntOffset) {
    val animatable = remember {
        Animatable(initialValue, Int.VectorConverter, null, label)
    }
    LaunchedEffect(animatable.value) {
        println("value($route): ${animatable.value}")
    }
    LaunchedEffect(routes.states.collectAsState().value.isCurrent(route = route)) {
        val width = windowInfo.containerSize.width
        val state = routes.states.value
        val value = when {
            !state.has(route = route) -> width
            state.isCurrent(route = route) -> 0
            else -> -width
        }
        animatable.animateTo(value, animationSpec)
    }
    return { IntOffset(x = animatable.value, y = 0) }
}
