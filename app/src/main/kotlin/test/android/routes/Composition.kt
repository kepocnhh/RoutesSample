package test.android.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
internal fun Composition(
    routes: Routes,
    routesTransitions: RoutesTransitions,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRoutes provides routes,
        LocalRoutesTransitions provides routesTransitions,
        content = content,
    )
}
