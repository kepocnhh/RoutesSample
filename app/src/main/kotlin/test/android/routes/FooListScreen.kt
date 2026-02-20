package test.android.routes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun FooListScreen(
    modifier: Modifier,
    onBack: () -> Unit,
) {
    RoutesAnimations(
        modifier = modifier,
        route = "foo:list",
        content = {
            FooListScreen(onBack = onBack)
        },
        foreground = {
            // todo
        },
    )
}

@Composable
internal fun FooListScreen(
    onBack: () -> Unit,
) {
    BackHandler(onBack = onBack)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
            .clickable(indication = null, interactionSource = null, onClick = { /* noop */ }),
    ) {
        // todo
    }
}
