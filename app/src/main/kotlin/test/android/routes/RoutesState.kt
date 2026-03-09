package test.android.routes

import java.util.Objects

class RoutesState(
    internal val stack: List<Route>,
    private val previous: Route?,
) {
    fun has(name: String): Boolean {
        for (route in stack) {
            if (route.name == name) return true
        }
        return false
    }

    fun <T : Any> has(type: Class<T>): Boolean {
        for (route in stack) {
            if (type.name == route.name) return true
        }
        return false
    }

    fun isCurrent(name: String): Boolean {
        val route = stack.lastOrNull()
        return name == route?.name
    }

    fun <T : Any> isCurrent(type: Class<T>): Boolean {
        val route = stack.lastOrNull()
        return type.name == route?.name
    }

    fun isPrevious(name: String): Boolean {
        return name == previous?.name
    }

    fun toForward(): Boolean {
        if (stack.size < 2) {
            return previous == null
        }
        return stack[stack.size - 2].name == previous?.name
    }

    fun <T : Any> getPayload(name: String, type: Class<T>): T? {
        for (route in stack) {
            if (route.name == name) {
                return type.cast(route.payload)
            }
        }
        TODO("not found $name")
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
