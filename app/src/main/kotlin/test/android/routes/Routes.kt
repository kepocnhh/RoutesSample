package test.android.routes

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Routes {
    data class State(val stack: List<String>)

    private val _states = MutableStateFlow(State(stack = emptyList()))
    val states = _states.asStateFlow()

    fun next(route: String) {
        val state = _states.value
        if (state.stack.contains(route)) TODO()
        _states.value = state.copy(stack = state.stack + route)
    }

    fun back() {
        val state = _states.value
        val stack = state.stack.toMutableList()
        if (stack.isEmpty()) TODO()
        stack.removeAt(stack.size - 1)
        _states.value = state.copy(stack = stack)
    }

    fun back(route: String) {
        val stack = mutableListOf<String>()
        val state = _states.value
        val current = state.stack.lastOrNull()
        if (route == current) TODO()
        for (it in state.stack) {
            stack += it
            if (it == route) {
                _states.value = state.copy(stack = stack)
                return
            }
        }
        TODO()
    }
}

val LocalRoutes = staticCompositionLocalOf { Routes() }
