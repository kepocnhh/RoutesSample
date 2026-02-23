package test.android.routes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

@Composable
internal fun MainScreen(
    modifier: Modifier,
    onBack: () -> Unit,
) {
    val routes = LocalRoutes.current
    val state = routes.states.collectAsState().value
    val windowInfo = LocalWindowInfo.current
    BackHandler(onBack = onBack)
    Box(modifier = modifier) {
        MainScreen(
            toObjects = {
                routes.next("foo:list")
            },
        )
    }
    RoutesAnimations(
        modifier = Modifier.fillMaxSize(),
        route = "foo:list",
    ) {
        val route = "foo:list" // todo
        FooListScreen(
            modifier = Modifier
                .fillMaxSize()
                .animateXOffset(
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                    label = route,
                    initialValue = { windowInfo.containerSize.width },
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
            onBack = routes::back,
        )
    }
}

@Composable
internal fun MainScreen(
    toObjects: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
            .clickable(indication = null, interactionSource = null, onClick = { /* noop */ }),
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
                    .clickable(onClick = toObjects)
                    .wrapContentSize(),
                text = "to objects",
            )
        }
    }
}
