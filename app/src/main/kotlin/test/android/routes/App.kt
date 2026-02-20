package test.android.routes

import android.app.Application
import test.android.routes.provider.FinalLocals
import test.android.routes.provider.Locals
import test.android.routes.provider.Providers

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val locals: Locals = FinalLocals()
        _providers = Providers(
            locals = locals,
        )
    }

    companion object {
        private var _providers: Providers? = null
        val providers get() = checkNotNull(_providers) { "No providers!" }
    }
}
