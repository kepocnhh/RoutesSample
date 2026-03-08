package test.android.routes

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import java.util.concurrent.atomic.AtomicLong
import kotlin.time.Duration

data class RoutesAnimationsScope(
    val route: String,
) {
    @Composable
    fun animateFloat(
        duration: Duration,
        easing: Easing,
        isForward: Boolean,
    ): Float {
        val values = remember { mutableFloatStateOf(0f) }
        val timeLeftState = remember { AtomicLong(0L) }
        LaunchedEffect(isForward) {
            val timeLeft = timeLeftState.get()
            val currentValue = values.floatValue
            val targetValue = if (isForward) 1f else 0f
            if (currentValue != targetValue) {
                val timeNanos = duration.inWholeNanoseconds
                val timeNow = withFrameNanos { it }
                val timeStart = timeNow - timeLeft
                while (true) {
                    val timePassed = withFrameNanos { it - timeStart }
                    if (timePassed < timeNanos) {
                        timeLeftState.set(timeNanos - timePassed)
                        val fraction = if (isForward) {
                            timePassed.toFloat().div(timeNanos)
                        } else {
                            timeNanos.minus(timePassed).toFloat().div(timeNanos)
                        }
                        values.floatValue = easing.transform(fraction = fraction)
                    } else {
                        timeLeftState.set(0)
                        values.floatValue = targetValue
                        break
                    }
                }
            }
        }
        return values.floatValue
    }
}
