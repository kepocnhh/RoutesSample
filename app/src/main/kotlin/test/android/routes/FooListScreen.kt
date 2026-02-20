package test.android.routes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.withContext
import test.android.routes.entity.Foo

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
    val routes: Routes = LocalRoutes.current
    val state = routes.states.collectAsState().value
    val providers = remember { App.providers }
    val insets = WindowInsets.systemBars.asPaddingValues()
    BackHandler(onBack = onBack)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .clickable(indication = null, interactionSource = null, onClick = { /* noop */ }),
    ) {
        val objectsState = remember { mutableStateOf(emptyList<Foo>()) }
        LaunchedEffect(Unit) {
            objectsState.value = withContext(providers.contexts.default) {
                providers.locals.objects
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = insets,
        ) {
            objectsState.value.forEach { obj ->
                item(key = obj.id) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        BasicText(
                            modifier = Modifier.fillMaxWidth(),
                            text = "id: ${obj.id}",
                        )
                        BasicText(
                            modifier = Modifier.fillMaxWidth(),
                            text = "text: ${obj.text}",
                        )
                        BasicText(
                            modifier = Modifier.fillMaxWidth(),
                            text = "number: ${obj.number}",
                        )
                    }
                }
            }
        }
    }
}
