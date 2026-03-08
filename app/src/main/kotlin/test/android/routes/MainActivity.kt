package test.android.routes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.coroutineScope

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ComposeView(this)
        setContentView(view)
        view.setContent {
            Composition(
                routes = Routes(
                    coroutineScope = lifecycle.coroutineScope,
                    default = App.providers.contexts.default,
                    stack = listOf(),
                ),
            ) {
                TestScreen()
            }
        }
    }
}
