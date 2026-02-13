package test.android.routes

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class Routes {
    private val _screens = MutableStateFlow<List<String>>(emptyList())
    val screens = _screens.asStateFlow()

    fun next(route: String) {
        _screens.value += route
    }

    fun back() {
        val value = _screens.value.toMutableList()
        if (value.isEmpty()) TODO()
        value.removeAt(value.size - 1)
        _screens.value = value
    }

    fun back(route: String) {
        val value = mutableListOf<String>()
        val actual = _screens.value
        for (it in actual) {
            value += it
            if (it == route) {
                _screens.value = value
                return
            }
        }
        TODO()
    }
}
