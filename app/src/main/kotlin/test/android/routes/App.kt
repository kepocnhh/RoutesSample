package test.android.routes

import android.app.Application
import kotlinx.coroutines.Dispatchers
import test.android.routes.provider.Contexts
import test.android.routes.provider.FinalLocals
import test.android.routes.provider.Locals
import test.android.routes.provider.Providers

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val locals: Locals = FinalLocals()
        val contexts = Contexts(
            main = Dispatchers.Main,
            default = Dispatchers.Default,
        )
        _providers = Providers(
            locals = locals,
            contexts = contexts,
        )
    }

    companion object {
        private var _providers: Providers? = null
        val providers get() = checkNotNull(_providers) { "No providers!" }
    }
}
