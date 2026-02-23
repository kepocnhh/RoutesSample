package test.android.routes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.withContext
import test.android.routes.entity.Foo
import java.util.UUID

@Composable
internal fun FooListScreen(
    modifier: Modifier,
    onBack: () -> Unit,
) {
    val providers = remember { App.providers }
    val objectsState = remember { mutableStateOf(emptyList<Foo>()) }
    LaunchedEffect(Unit) {
        objectsState.value = withContext(providers.contexts.default) {
            providers.locals.objects
        }
    }
    BackHandler(onBack = onBack)
    Box(modifier = modifier) {
        FooListScreen(
            objects = objectsState.value,
            toObject = { id ->
                // todo
            },
        )
    }
    // todo object
}

@Composable
internal fun FooListScreen(
    objects: List<Foo>,
    toObject: (UUID) -> Unit,
) {
    val insets = WindowInsets.systemBars.asPaddingValues()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .clickable(indication = null, interactionSource = null, onClick = { /* noop */ }),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = insets.calculateTopPadding() + 8.dp,
                bottom = insets.calculateBottomPadding() + 8.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            objects.forEach { obj ->
                item(key = obj.id) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                toObject(obj.id)
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
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
