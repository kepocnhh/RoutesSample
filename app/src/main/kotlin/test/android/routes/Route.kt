package test.android.routes

import java.util.Objects

class Route internal constructor(
    val name: String,
    val payload: Any?,
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Route -> name == other.name && payload == other.payload
            else -> false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(name, payload)
    }

    override fun toString(): String {
        return "Route" // todo
    }
}
