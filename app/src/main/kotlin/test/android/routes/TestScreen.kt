package test.android.routes

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun TestScreen() {
    val insets = WindowInsets.systemBars.asPaddingValues()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(insets),
        ) {
            val isVisibleState = remember { mutableStateOf(false) }
            val animatable = remember {
                Animatable(0f, Float.VectorConverter)
            }
            val spec = remember { tween<Float>(durationMillis = 2_000, easing = LinearEasing) }
            LaunchedEffect(isVisibleState.value) {
                val targetValue = if (isVisibleState.value) 1f else 0f
                animatable.animateTo(targetValue, spec)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = Color.Red),
            ) {
                if (isVisibleState.value || animatable.value > 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(animatable.value)
                            .background(color = Color.Green),
                    ) {
                        BasicText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            text = "alpha: ${animatable.value}",
                        )
                    }
                }
            }
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        isVisibleState.value = !isVisibleState.value
                    }
                    .wrapContentSize(),
                text = if (isVisibleState.value) "hide" else "show",
            )
        }
    }
}
