package test.android.routes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun MainScreen() {
    val routes = LocalRoutes.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { routes.next(route = "v0") }
                    .wrapContentSize(),
                text = "to -> v0",
            )
        }
    }
    V0Screen(
        routes = routes,
        onBack = routes::back,
    )
}

@Composable
internal fun RouteScreen(
    modifier: Modifier,
    routes: Routes,
    route: String,
    content: @Composable BoxScope.() -> Unit,
    foreground: @Composable BoxScope.() -> Unit,
) {
    val state = routes.states.collectAsState().value
    BoxWithConstraints(modifier = modifier) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = state.stack.contains(route),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            val animatable = remember { Animatable(maxWidth, Dp.VectorConverter, null, route) }
            LaunchedEffect(state.stack.size) {
                val value = when {
                    !state.stack.contains(route) -> maxWidth
                    route == state.stack.lastOrNull() -> 0.dp
                    else -> -maxWidth
                }
                animatable.animateTo(value, tween(easing = FastOutSlowInEasing))
            }
            Box(modifier = Modifier.fillMaxSize().offset(x = animatable.asState().value), content = content)
            Box(modifier = Modifier.fillMaxSize(), content = foreground)
        }
    }
}

@Composable
internal fun V0Screen(
    routes: Routes,
    onBack: () -> Unit,
) {
    RouteScreen(
        modifier = Modifier.fillMaxSize(),
        routes = routes,
        route = "v0",
        content = {
            V0Screen(onBack = onBack)
        },
        foreground = {
            V1Screen(routes = routes, onBack = routes::back)
        },
    )
}

@Composable
internal fun V0Screen(onBack: () -> Unit) {
    val routes = LocalRoutes.current
    BackHandler(onBack = onBack)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red, shape = RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { /* noop */ },
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .wrapContentSize(),
                text = "v0",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        routes.next(route = "v1")
                    }
                    .wrapContentSize(),
                text = "to -> v1",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable(onClick = onBack)
                    .wrapContentSize(),
                text = "back",
            )
        }
    }
}

@Composable
internal fun V1Screen(
    routes: Routes,
    onBack: () -> Unit,
) {
    RouteScreen(
        modifier = Modifier.fillMaxSize(),
        routes = routes,
        route = "v1",
        content = {
            V1Screen(onBack = onBack)
        },
        foreground = {
            V2Screen(
                routes = routes,
                onBack = routes::back,
                onComplete = {
                    routes.back(route = "v0")
                },
            )
        },
    )
}

@Composable
internal fun V1Screen(onBack: () -> Unit) {
    val routes = LocalRoutes.current
    BackHandler(onBack = onBack)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { /* noop */ },
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .wrapContentSize(),
                text = "v1",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        routes.next(route = "v2")
                    }
                    .wrapContentSize(),
                text = "to -> v2",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable(onClick = onBack)
                    .wrapContentSize(),
                text = "back",
            )
        }
    }
}

@Composable
internal fun V2Screen(
    routes: Routes,
    onBack: () -> Unit,
    onComplete: () -> Unit,
) {
    RouteScreen(
        modifier = Modifier.fillMaxSize(),
        routes = routes,
        route = "v2",
        content = {
            V2Screen(onBack = onBack, onComplete = onComplete)
        },
        foreground = {
            // todo
        },
    )
}

@Composable
internal fun V2Screen(
    onBack: () -> Unit,
    onComplete: () -> Unit,
) {
    BackHandler(onBack = onBack)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { /* noop */ },
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .wrapContentSize(),
                text = "v2",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        onComplete()
                    }
                    .wrapContentSize(),
                text = "complete",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable(onClick = onBack)
                    .wrapContentSize(),
                text = "back",
            )
        }
    }
}
