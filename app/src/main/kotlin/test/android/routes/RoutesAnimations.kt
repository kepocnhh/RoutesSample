package test.android.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun RoutesAnimations(
    modifier: Modifier,
    route: String,
    routes: Routes = LocalRoutes.current,
    content: @Composable RoutesAnimationsScope.() -> Unit,
) {
    val scope = remember { RoutesAnimationsScope() }
    val isVisible = routes.states.collectAsState().value.has(route = route)
    val isLoading = scope._actions.collectAsState().value.isNotEmpty()
    LaunchedEffect(isLoading) {
        println("actions: ${scope._actions.value}")
    }
    LaunchedEffect(isVisible) {
        val state = routes.states.value
        if (state.has(route = route)) {
            // todo
        } else if (!state.isPrevious(route = route)) {
            // todo
        } else {
            // todo
        }
    }
    if (isVisible || isLoading) {
        Box(modifier = modifier) {
            scope.content()
            DisposableEffect(Unit) {
                onDispose {
                    // todo
                }
            }
        }
    }
}
