package test.android.routes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
