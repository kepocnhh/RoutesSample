package test.android.routes

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun MainScreen(
    modifier: Modifier,
    onBack: () -> Unit,
) {
    RoutesAnimations(
        modifier = modifier,
        route = "main",
        content = { routes ->
            MainScreen(
                onBack = onBack,
                toList = {
                    routes.next("foo:list")
                },
            )
        },
        foreground = { routes ->
            FooListScreen(
                modifier = Modifier.fillMaxSize(),
                onBack = routes::back,
            )
        },
    )
}

@Composable
internal fun MainScreen(
    onBack: () -> Unit,
    toList: () -> Unit,
) {
    BackHandler(onBack = onBack)
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
                    .clickable(onClick = toList)
                    .wrapContentSize(),
                text = "to objects",
            )
        }
    }
}
