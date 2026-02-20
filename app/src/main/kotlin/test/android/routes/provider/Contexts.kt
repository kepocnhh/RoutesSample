package test.android.routes.provider

import kotlinx.coroutines.CoroutineDispatcher

internal class Contexts(
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
)
