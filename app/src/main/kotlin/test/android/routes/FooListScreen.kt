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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
    val isLoading = routes.loading.collectAsState().value
    val state = routes.states.collectAsState().value
//    val duration = 250.milliseconds
    val duration = 500.milliseconds
//    val duration = 1.seconds
//    val duration = 2.seconds
//    val duration = 4.seconds
//    val easing = LinearEasing
    val easing = FastOutSlowInEasing
    val fraction = animateFloat(
        duration = duration,
        easing = easing,
        isForward = state.isCurrent(name = "foo:list"),
    )
    val width = LocalWindowInfo.current.containerSize.width
//    val scale = 0.9f + 0.1f * fraction
    val scale = 0.75f + 0.25f * fraction
//    val scale = 0.5f + 0.5f * fraction
//    val alpha = 0.5f + 0.5f * fraction
    val alpha = 1f * fraction
    val translationX = if (state.isCurrent(name = "foo:list")) {
        if (state.toForward()) {
            width - width * fraction
        } else {
            width * fraction - width
        }
    } else {
        if (state.has(name = "foo:list")) {
            width * fraction - width
        } else {
            width - width * fraction
        }
    }
    BackHandler {
        onBack()
    }
    val wi = LocalActivity.current?.window?.decorView?.rootWindowInsets
    val corners = RoundedCornerShape(
        topStart = wi?.getRoundedCorner(RoundedCorner.POSITION_TOP_LEFT)?.radius?.toFloat() ?: 0f,
        topEnd = wi?.getRoundedCorner(RoundedCorner.POSITION_TOP_RIGHT)?.radius?.toFloat() ?: 0f,
        bottomEnd = wi?.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_RIGHT)?.radius?.toFloat() ?: 0f,
        bottomStart = wi?.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_LEFT)?.radius?.toFloat() ?: 0f,
    )
    val density = LocalDensity.current.density
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = translationX,
                transformOrigin = TransformOrigin(0f, 0.5f),
                clip = true,
                shape = corners,
                shadowElevation = if (isLoading) density * 8 else 0f,
            )
            .background(color = Color.White)
            .graphicsLayer(alpha = alpha),
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
            toDetail = { foo ->
                routes.next(payload = foo)
            },
        )
    }
    RoutesAnimations(
        modifier = Modifier.fillMaxSize(),
        type = Foo::class.java,
    ) { foo ->
        FooDetailScreen(
            foo = foo ?: TODO(),
            onBack = routes::back,
        )
    }
}

@Composable
internal fun FooListScreen(
    list: List<Foo>,
    toDetail: (Foo) -> Unit,
) {
    val routes = LocalRoutes.current
    val isLoading = routes.loading.collectAsState().value
    val insets = WindowInsets.systemBars.asPaddingValues()
    Box(
        modifier = Modifier.fillMaxSize(),
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
                            .clickable(enabled = !isLoading) {
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
