package test.android.routes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ComposeView(this)
        setContentView(view)
//        val duration = 250.milliseconds
        val duration = 2.seconds
//        val easing: Easing = LinearEasing
        val easing: Easing = FastOutSlowInEasing
        view.setContent {
            Composition(
                routes = Routes(stack = listOf("main")),
                routesTransitions = RoutesTransitions(
                    enter = fadeIn(animationSpec = tween(durationMillis = duration.inWholeMilliseconds.toInt(), easing = easing)),
                    exit = fadeOut(animationSpec = tween(durationMillis = duration.inWholeMilliseconds.toInt(), easing = easing)),
                ),
                routesSpecs = RoutesSpecs(
                    x = tween(durationMillis = duration.inWholeMilliseconds.toInt(), easing = easing),
                ),
            ) {
                RoutesAnimations(
                    modifier = Modifier.fillMaxSize().background(color = Color.White),
                    route = "main",
                ) {
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(animateXOffset(route = "main", initialValue = 0)), // todo RoutesModifierNodeElement
                        onBack = ::finish,
                    )
                }
            }
        }
    }
}
