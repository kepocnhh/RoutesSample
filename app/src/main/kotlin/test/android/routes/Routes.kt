package test.android.routes

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class Routes {
    data class State(
        val stack: List<String>,
        val previous: String?,
    )

    private val _states = MutableStateFlow(State(stack = emptyList(), previous = null))
    val states = _states.asStateFlow()

    fun next(route: String) {
        val state = _states.value
        if (state.stack.contains(route)) TODO()
        _states.value = state.copy(
            stack = state.stack + route,
            previous = state.stack.lastOrNull(),
        )
    }

    fun back() {
        val state = _states.value
        val stack = state.stack.toMutableList()
        if (stack.isEmpty()) TODO()
        val previous = stack.removeAt(stack.size - 1)
        _states.value = state.copy(
            stack = stack,
            previous = previous,
        )
    }

    fun back(route: String) {
        val stack = mutableListOf<String>()
        val state = _states.value
        val previous = state.stack.lastOrNull()
        if (route == previous) TODO()
        for (it in state.stack) {
            stack += it
            if (it == route) {
                _states.value = state.copy(
                    stack = stack,
                    previous = previous,
                )
                return
            }
        }
        TODO()
    }
}
