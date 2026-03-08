package test.android.routes

import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun TestScreen() {
    val insets = WindowInsets.systemBars.asPaddingValues()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(insets),
        ) {
            val routes = LocalRoutes.current
            val state = routes.states.collectAsState().value
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = Color.Red),
            ) {
                RoutesAnimations(
                    modifier = Modifier
                        .fillMaxSize(),
                    route = "test",
                ) {
                    val alpha = animateFloat(
                        duration = 2.seconds,
                        easing = LinearEasing,
                        isForward = state.has(route = "test"),
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(alpha = alpha)
                            .background(color = Color.Blue),
                    ) {
                        BasicText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            text = "alpha: $alpha",
                        )
                    }
                }
            }
            val isLoading = routes.loading.collectAsState().value
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable(enabled = !isLoading) {
                        if (routes.states.value.has(route = "test")) {
                            routes.back()
                        } else {
                            routes.next(route = "test")
                        }
                    }
                    .wrapContentSize(),
                text = if (state.has(route = "test")) "hide" else "show",
            )
        }
    }
}
