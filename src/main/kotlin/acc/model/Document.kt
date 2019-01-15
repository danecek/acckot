package acc.model

import java.time.LocalDate

class Document(
        val type: DocumentType,
        val number: String,
        val date: LocalDate,
        val description: String) {

    val id: DocumentId
        get() = DocumentId(type, number)
}