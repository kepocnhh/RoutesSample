package test.android.routes

import android.view.RoundedCorner
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import test.android.routes.entity.Foo
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun RoutesAnimationsScope.FooListScreen(
    onBack: () -> Unit,
) {
    val routes = LocalRoutes.current
    val state = routes.states.collectAsState().value
    val duration = 250.milliseconds
//    val duration = 1.seconds
//    val easing = LinearEasing
    val easing = FastOutSlowInEasing
    val fraction = animateFloat(
        duration = duration,
        easing = easing,
        isForward = state.isCurrent(route = "foo:list"),
    )
    val width = LocalWindowInfo.current.containerSize.width
    val scale = 0.9f + 0.1f * fraction
    val w = animateFloat(
        duration = duration,
        easing = easing,
        isForward = state.isCurrent(route = "foo:list"),
    )
    val translationX = if (state.isCurrent(route = "foo:list")) {
        if (state.isForward()) {
            width - width * w
        } else {
            width * w - width
        }
    } else {
        if (state.has(route = "foo:list")) {
            width * w - width
        } else {
            width - width * w
        }
    }
    BackHandler {
        onBack()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                alpha = fraction,
                scaleX = scale,
                scaleY = scale,
                translationX = translationX,
            ),
    ) {
        val list = remember {
            // todo
            (0 until 32).map { index ->
                Foo(
                    id = UUID(0, index.toLong()),
                    text = "text - $index",
                    number = index,
                )
            }
        }
        FooListScreen(
            list = list,
            toDetail = {
                routes.next("foo:detail")
            },
        )
    }
    RoutesAnimations(
        modifier = Modifier.fillMaxSize(),
        route = "foo:detail",
    ) {
        FooDetailScreen(
            onBack = routes::back,
        )
    }
}

@Composable
internal fun FooListScreen(
    list: List<Foo>,
    toDetail: (Foo) -> Unit,
) {
    val insets = WindowInsets.systemBars.asPaddingValues()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = insets,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            list.forEach { foo ->
                item(key = foo.id) {
                    val text = """
                        id: ${foo.id}
                        text: "${foo.text}"
                        number: ${foo.number}
                    """.trimIndent()
                    BasicText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                toDetail(foo)
                            }
                            .padding(horizontal = 16.dp),
                        text = text,
                    )
                }
            }
        }
    }
}
