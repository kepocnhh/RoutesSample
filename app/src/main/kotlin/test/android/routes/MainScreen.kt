package test.android.routes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
internal fun MainScreen(
    onBack: () -> Unit,
) {
    val routes = LocalRoutes.current
    val state = routes.states.collectAsState().value
    BackHandler(onBack = onBack)
    val width: Int = LocalWindowInfo.current.containerSize.width
    val animationSpec: AnimationSpec<Int> = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    val animatable = remember {
        Animatable(0, Int.VectorConverter, null, "main")
    }
    LaunchedEffect(state.stack.isEmpty()) {
        val value = if (routes.states.value.stack.isEmpty()) 0 else -width
        animatable.animateTo(value, animationSpec)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
            .offset { IntOffset(x = animatable.value, y = 0) }
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
                    .clickable {
                        routes.next("foo:list")
                    }
                    .wrapContentSize(),
                text = "to objects",
            )
        }
    }
    RoutesAnimations(
        modifier = Modifier.fillMaxSize(),
        state = state,
        route = "foo:list",
        transitions = LocalRoutesTransitions.current,
    ) {
        val offset = IntOffset(x = animateXOffset(route = "foo:list"), y = 0)
        FooListScreen(
            modifier = Modifier.fillMaxSize().offset { offset },
            onBack = routes::back,
        )
    }
}
