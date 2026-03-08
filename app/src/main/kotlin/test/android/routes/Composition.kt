package test.android.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
internal fun Composition(
    routes: Routes,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRoutes provides routes,
        content = content,
    )
}
