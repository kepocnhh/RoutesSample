package test.android.routes

import java.util.Objects

class RoutesState(
    internal val stack: List<String>,
    private val previous: String?,
) {
    fun has(route: String): Boolean {
        return stack.contains(route)
    }

    fun isCurrent(route: String): Boolean {
        return route == stack.lastOrNull()
    }

    fun isPrevious(route: String): Boolean {
        return route == previous
    }

    fun isForward(): Boolean {
        return stack.getOrNull(stack.size - 2) == previous
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is RoutesState -> stack == other.stack && previous == other.previous
            else -> false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(stack, previous)
    }

    override fun toString(): String {
        return "RoutesState" // todo
    }
}
