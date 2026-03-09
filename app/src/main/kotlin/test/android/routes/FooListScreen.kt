package test.android.routes

import android.view.RoundedCorner
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import test.android.routes.entity.Foo
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun RoutesAnimationsScope.FooListScreen() {
    val routes = LocalRoutes.current
    val state = routes.states.collectAsState().value
    val duration = 250.milliseconds
//    val duration = 2.seconds
//    val easing = LinearEasing
    val easing = FastOutSlowInEasing
    val fraction = animateFloat(
        duration = duration,
        easing = easing,
        isForward = state.has(route = "foo:list"),
    )
    val wi = LocalActivity.current?.window?.decorView?.rootWindowInsets
    val radius = wi?.getRoundedCorner(RoundedCorner.POSITION_TOP_LEFT)?.radius ?: 0
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red, shape = RoundedCornerShape(size = radius.toFloat())),
    ) {
        // todo
    }
}

@Composable
internal fun FooListScreen(list: List<Foo>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        // todo
    }
}
