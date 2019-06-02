package acc.model

class DocId(val type: DocType, val number: Int) : Comparable<DocId> {

    override fun compareTo(other: DocId): Int {
        val d = type.compareTo(other.type)
        return if (d != 0) d
        else number.compareTo(other.number)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        return if (other !is DocId) false
        else compareTo(other) == 0
    }

    override fun hashCode(): Int {
        return type.hashCode() + number.hashCode()
    }

    override fun toString(): String = "${type.abbr}$number"

}
