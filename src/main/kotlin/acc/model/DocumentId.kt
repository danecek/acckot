package acc.model

class DocumentId(val type: DocumentType, val number: Int) : Comparable<DocumentId> {
    override fun compareTo(other: DocumentId): Int {
        val d = type.compareTo(other.type)
        return if (d != 0) d
        else number.compareTo(other.number)
    }

    override fun equals(other: Any?): Boolean {
        return compareTo(other as DocumentId) == 0
    }
    override fun hashCode(): Int {
        return type.hashCode() + number.hashCode()
    }

    override fun toString(): String = type.toString()+number

}
