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
    val scopes = remember {
        val scope = RoutesAnimationsScope(route = route)
        mutableStateOf(scope)
    }
    val isVisible = routes.states.collectAsState().value.has(route = route)
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
    if (isVisible) {
        Box(modifier = modifier) {
            scopes.value.content()
            DisposableEffect(Unit) {
                onDispose {
                    // todo
                }
            }
        }
    }
}
