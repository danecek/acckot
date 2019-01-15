package acc.model

class DocumentId(val type: DocumentType, val name: String) : Comparable<DocumentId> {
    override fun compareTo(other: DocumentId): Int {
        val d = type.compareTo(other.type)
        return if (d != 0) d
        else  name.compareTo(other.name)
    }

}
