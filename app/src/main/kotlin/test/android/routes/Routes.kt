package test.android.routes

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class Routes(
    private val coroutineScope: CoroutineScope,
    private val default: CoroutineContext,
    stack: List<String>,
) {
    private val mutex = Mutex()
    private val _states = MutableStateFlow(RoutesState(stack = stack, previous = null))
    val states = _states.asStateFlow()
    private val _actions = MutableStateFlow<Set<String>>(emptySet())
    val loading = object : StateFlow<Boolean> {
        override val value: Boolean get() = _actions.value.isNotEmpty()
        override val replayCache: List<Boolean> = emptyList()

        override suspend fun collect(collector: FlowCollector<Boolean>): Nothing {
            var value: Boolean? = null
            _actions.collect { labels ->
                val isLoading = labels.isNotEmpty()
                if (isLoading != value) {
                    value = isLoading
                    collector.emit(isLoading)
                }
            }
        }
    }

    init {
        // todo
        coroutineScope.launch {
            withContext(default) {
                _states.collect { state ->
                    println("[Routes]:stack: ${state.stack}")
                }
            }
        }
        // todo
    }

    fun next(route: String) {
        coroutineScope.launch {
            withContext(default) {
                _states.value = mutex.withLock {
                    if (loading.value) TODO()
                    val state = _states.value
                    if (state.stack.contains(route)) TODO()
                    RoutesState(
                        stack = state.stack + route,
                        previous = state.stack.lastOrNull(),
                    )
                }
            }
        }
    }

    fun back() {
        coroutineScope.launch {
            withContext(default) {
                _states.value = mutex.withLock {
                    if (loading.value) TODO()
                    val state = _states.value
                    val stack = state.stack.toMutableList()
                    if (stack.isEmpty()) TODO()
                    stack.removeAt(stack.size - 1)
                    RoutesState(
                        stack = stack,
                        previous = state.stack.lastOrNull() ?: TODO(),
                    )
                }
            }
        }
    }
}

val LocalRoutes = staticCompositionLocalOf<Routes> { error("No routes!") }
