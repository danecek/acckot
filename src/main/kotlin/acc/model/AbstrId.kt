package acc.model

import java.io.Serializable

abstract class AbstrId<T> : Comparable<T>, Serializable {

    abstract val id : Int

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this.javaClass != other.javaClass) {
            return false
        }
        val bookId = other as T?
        return compareTo(bookId!!) == 0
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 17 * hash + (this.id xor this.id.ushr(32))
        return hash
    }

    override fun compareTo(other: T): Int {
        return this.id - (other as AbstrId<*>) .id
    }

    override fun toString(): String {
        return Integer.toString(id)
    }

}
