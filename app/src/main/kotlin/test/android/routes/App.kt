package test.android.routes

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // todo
    }

    companion object {
        private val routes = Routes()

        @Composable
        fun routes(): Routes {
            return remember { routes }
        }
    }
}
