package test.android.routes

import android.view.RoundedCorner
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import test.android.routes.entity.Foo
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun RoutesAnimationsScope.FooDetailScreen(
    foo: Foo,
    onBack: () -> Unit,
) {
    val routes = LocalRoutes.current
    val isLoading = routes.loading.collectAsState().value
    val state = routes.states.collectAsState().value
//    val duration = 250.milliseconds
    val duration = 500.milliseconds
//    val duration = 1.seconds
//    val duration = 2.seconds
//    val easing = LinearEasing
    val easing = FastOutSlowInEasing
    val fraction = animateFloat(
        duration = duration,
        easing = easing,
        isForward = state.isCurrent(type = Foo::class.java),
    )
    val width = LocalWindowInfo.current.containerSize.width
//    val scale = 0.9f + 0.1f * fraction
    val scale = 0.75f + 0.25f * fraction
//    val scale = 0.5f + 0.5f * fraction
//    val alpha = 0.5f + 0.5f * fraction
    val alpha = 1f * fraction
    val translationX = if (state.isCurrent(type = Foo::class.java)) {
        if (state.toForward()) {
            width - width * fraction
        } else {
            width * fraction - width
        }
    } else {
        if (state.has(type = Foo::class.java)) {
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
        FooDetailScreen(foo = foo)
    }
}

@Composable
internal fun FooDetailScreen(foo: Foo) {
    val insets = WindowInsets.systemBars.asPaddingValues()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            val text = """
                id: ${foo.id}
                text: "${foo.text}"
                number: ${foo.number}
            """.trimIndent()
            BasicText(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                text = text,
            )
        }
    }
}
