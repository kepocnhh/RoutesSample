package test.android.routes

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FloatAnimationSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.debugInspectorInfo

data class RoutesAnimationsScope(
    val route: String,
) {
    @Composable
    fun animateFloat(
        initialValue: Float,
        targetValue: Float,
        animationSpec: FloatAnimationSpec,
    ): Float {
        val values = remember { mutableStateOf(initialValue) }
        LaunchedEffect(targetValue) {
            val timeStart = withFrameNanos { it }
            val timeNanos = animationSpec.getDurationNanos(
                initialValue = initialValue,
                targetValue = targetValue,
                initialVelocity = 0f,
            )
            println("start")
            while (true) {
                val timeDiff = withFrameNanos { it - timeStart }
                val isActive = timeDiff < timeNanos
                if (isActive) {
                    values.value = animationSpec.getValueFromNanos(
                        playTimeNanos = timeDiff,
                        initialValue = initialValue,
                        targetValue = targetValue,
                        initialVelocity = 0f,
                    )
                } else {
                    values.value = animationSpec.getValueFromNanos(
                        playTimeNanos = timeNanos,
                        initialValue = initialValue,
                        targetValue = targetValue,
                        initialVelocity = 0f,
                    )
                    break
                }
            }
            println("finish")
        }
        return values.value
    }

    @Composable
    fun animateAlpha(
        route: String = this.route,
        routes: Routes = LocalRoutes.current,
        label: String = route,
        animationSpec: AnimationSpec<Float>,
    ): Float {
        val animatable = remember {
            Animatable(0f, Float.VectorConverter, null, label)
        }
        LaunchedEffect(routes.states.collectAsState().value.isCurrent(route = route)) {
            val isCurrent = routes.states.value.isCurrent(route = route)
            val targetValue = if (isCurrent) 1f else 0f
            animatable.animateTo(targetValue, animationSpec)
        }
        return animatable.value
    }

    fun Modifier.animateAlpha(
        route: String,
        routes: Routes,
        label: String,
        animationSpec: AnimationSpec<Float>,
    ): Modifier {
        return composed(
            inspectorInfo = debugInspectorInfo {
                name = "animateAlpha"
                // todo
            },
            factory = {
                val animatable = remember {
                    Animatable(0f, Float.VectorConverter, null, label)
                }
                LaunchedEffect(routes.states.collectAsState().value.isCurrent(route = route)) {
                    val isCurrent = routes.states.value.isCurrent(route = route)
                    val targetValue = if (isCurrent) 1f else 0f
                    animatable.animateTo(targetValue, animationSpec)
                }
                Modifier.alpha(alpha = animatable.value)
            },
        )
    }
}
