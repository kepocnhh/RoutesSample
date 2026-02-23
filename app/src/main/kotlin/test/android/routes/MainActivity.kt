package test.android.routes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.platform.ComposeView
import kotlin.time.Duration.Companion.milliseconds

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ComposeView(this)
        setContentView(view)
        val duration = 250.milliseconds
        view.setContent {
            Composition(
                routes = Routes(stack = listOf()),
                routesTransitions = RoutesTransitions(
                    enter = fadeIn(animationSpec = tween(durationMillis = duration.inWholeMilliseconds.toInt(), easing = LinearEasing)),
                    exit = fadeOut(animationSpec = tween(durationMillis = duration.inWholeMilliseconds.toInt(), easing = LinearEasing)),
                ),
            ) {
                MainScreen(onBack = ::finish)
            }
        }
    }
}
