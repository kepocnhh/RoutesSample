package test.android.routes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun MainScreen() {
    val routes = App.routes()
    val screens = routes.screens.collectAsState().value
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
                    .clickable { routes.next(route = "foo") }
                    .wrapContentSize(),
                text = "foo",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { routes.next(route = "bar") }
                    .wrapContentSize(),
                text = "bar",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { routes.next(route = "v0") }
                    .wrapContentSize(),
                text = "to -> v0",
            )
        }
        AnimatedVisibility(
            visible = screens.contains("foo"),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            FooScreen(onBack = routes::back)
        }
        AnimatedVisibility(
            visible = screens.contains("bar"),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            BarScreen(onBack = routes::back)
        }
        AnimatedVisibility(
            visible = screens.contains("v0"),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            V0Screen(onBack = routes::back)
        }
    }
}

@Composable
internal fun FooScreen(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { /* noop */ },
            ),
    ) {
        BackHandler(onBack = onBack)
    }
}

@Composable
internal fun BarScreen(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { /* noop */ },
            ),
    ) {
        BackHandler(onBack = onBack)
    }
}

@Composable
internal fun V0Screen(onBack: () -> Unit) {
    val routes = App.routes()
    val screens = routes.screens.collectAsState().value
    val size = LocalWindowInfo.current.containerSize
    val animatable = remember { Animatable(size.width.dp, Dp.VectorConverter, null, "v0") }
    LaunchedEffect(screens.size) {
//        val value = if ("v0" == screens.lastOrNull()) 0.dp else size.width.dp
        val value = when {
            !screens.contains("v0") -> size.width.dp
            "v0" == screens.lastOrNull() -> 0.dp
            else -> -size.width.dp
        }
        animatable.animateTo(value, tween(easing = LinearEasing))
    }
    val x = animatable.asState().value
    LaunchedEffect(x) {
        println("x: $x")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = x)
            .background(Color.Red)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { /* noop */ },
            ),
    ) {
        BackHandler(onBack = onBack)
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
                    .clickable { routes.next(route = "v1") }
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
    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = screens.contains("v1"),
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        V1Screen(onBack = routes::back)
    }
}

@Composable
internal fun V1Screen(onBack: () -> Unit) {
    val routes = App.routes()
    val screens = routes.screens.collectAsState().value
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
        BackHandler(onBack = onBack)
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
                    .clickable { routes.next(route = "v2") }
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
        AnimatedVisibility(
            visible = screens.contains("v2"),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            V2Screen(onBack = routes::back)
        }
    }
}

@Composable
internal fun V2Screen(onBack: () -> Unit) {
    val routes = App.routes()
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
        BackHandler(onBack = onBack)
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
                    .clickable { routes.back(route = "v0") }
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
