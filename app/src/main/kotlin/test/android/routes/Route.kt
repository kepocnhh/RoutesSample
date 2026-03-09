package test.android.routes

import java.util.Objects

class Route internal constructor(
    val name: String,
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Route -> name == other.name
            else -> false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(name)
    }

    override fun toString(): String {
        return "Route" // todo
    }
}
