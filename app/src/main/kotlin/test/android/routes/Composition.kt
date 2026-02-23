package test.android.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
internal fun Composition(
    routes: Routes,
    routesTransitions: RoutesTransitions,
    routesSpecs: RoutesSpecs,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRoutes provides routes,
        LocalRoutesTransitions provides routesTransitions,
        LocalRoutesSpecs provides routesSpecs,
        content = content,
    )
}
