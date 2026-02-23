package test.android.routes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalWindowInfo
import kotlin.time.Duration.Companion.milliseconds

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ComposeView(this)
        setContentView(view)
        val duration = 250.milliseconds
        val easing: Easing = LinearEasing
        view.setContent {
            Composition(
                routes = Routes(stack = listOf("main")),
                routesTransitions = RoutesTransitions(
                    enter = fadeIn(animationSpec = tween(durationMillis = duration.inWholeMilliseconds.toInt(), easing = easing)),
                    exit = fadeOut(animationSpec = tween(durationMillis = duration.inWholeMilliseconds.toInt(), easing = easing)),
                ),
            ) {
                val routes = LocalRoutes.current
                val state = routes.states.collectAsState().value
                val windowInfo = LocalWindowInfo.current
                RoutesAnimations(
                    modifier = Modifier.fillMaxSize(),
                    route = "main",
                ) {
                    val route = "main"
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .animateXOffset(
                                animationSpec = tween(durationMillis = duration.inWholeMilliseconds.toInt(), easing = easing),
                                label = route,
                                initialValue = { 0 },
                                whenAnimate = { route == state.stack.lastOrNull() },
                                targetValue = {
                                    val width = windowInfo.containerSize.width
                                    val stack = routes.states.value.stack
                                    when {
                                        !stack.contains(route) -> width
                                        route == stack.lastOrNull() -> 0
                                        else -> -width
                                    }
                                },
                            ),
                        onBack = ::finish,
                    )
                }
            }
        }
    }
}
