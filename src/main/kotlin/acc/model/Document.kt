package acc.model

import java.time.LocalDate

class Document(
        val type: DocType,
        val number: Int,
        var date: LocalDate,
        var description: String) {

    val id: DocId
        get() = DocId(type, number)

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other !is Document) return false
        return id == other.id
    }

    override fun toString(): String {
        return "${type.abbr}$number - ${description.take(30)}"
    }
}