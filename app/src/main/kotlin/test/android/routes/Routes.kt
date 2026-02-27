package test.android.routes

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

class Routes(
    private val coroutineScope: CoroutineScope,
    private val default: CoroutineContext,
    stack: List<String>,
) {
    private val mutex = Mutex()
    private val _states = MutableStateFlow(RoutesState(stack = stack, previous = null))
    val states = _states.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun next(route: String) {
        coroutineScope.launch {
            withContext(default) {
                _states.value = mutex.withLock {
                    if (_loading.value) TODO()
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
                    if (_loading.value) TODO()
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

//    fun back(route: String) {
//        val stack = mutableListOf<String>()
//        val state = _states.value
//        val current = state.stack.lastOrNull()
//        if (route == current) TODO()
//        for (it in state.stack) {
//            stack += it
//            if (it == route) {
//                _states.value = state.copy(stack = stack)
//                return
//            }
//        }
//        TODO()
//    }

    internal fun startAnimation(label: String) {
        println("start animation: $label")
        coroutineScope.launch {
            withContext(default) {
                mutex.withLock {
                    _loading.value = true
                }
            }
        }
    }

    internal fun finishAnimation(label: String) {
        println("finish animation: $label")
        coroutineScope.launch {
            withContext(default) {
                mutex.withLock {
                    _loading.value = false
                }
            }
        }
    }
}

val LocalRoutes = staticCompositionLocalOf<Routes> { error("No routes!") }
