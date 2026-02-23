package test.android.routes

data class RoutesState(internal val stack: List<String>) {
    fun has(route: String): Boolean {
        return stack.contains(route)
    }

    fun isCurrent(route: String): Boolean {
        return route == stack.lastOrNull()
    }
}
