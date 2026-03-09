package test.android.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun RoutesAnimations(
    modifier: Modifier,
    name: String,
    routes: Routes = LocalRoutes.current,
    content: @Composable RoutesAnimationsScope.() -> Unit,
) {
    val scope = remember { RoutesAnimationsScope() }
    val isVisible = routes.states.collectAsState().value.has(name = name)
    val isLoading = scope._actions.collectAsState().value.isNotEmpty()
    LaunchedEffect(Unit) {
        scope._actions.collect { actions ->
            if (actions.values.contains(true)) {
                routes._actions.value += name
            } else {
                routes._actions.value -= name
            }
        }
    }
    if (isVisible || isLoading) {
        Box(modifier = modifier) {
            scope.content()
        }
    }
}
