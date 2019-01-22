package acc.model

import java.time.LocalDate

class Document(
        val type: DocumentType,
        val number: Int,
        val date: LocalDate,
        val description: String) {

    val id: DocumentId
        get() = DocumentId(type, number)

    override fun toString(): String {
        return "${type.text}$number - ${description.take(30)}"
    }
}