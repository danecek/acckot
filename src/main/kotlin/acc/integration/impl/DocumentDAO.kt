package acc.integration.impl

import acc.model.Document
import acc.model.DocumentId
import acc.model.DocumentType
import acc.util.AccException
import java.time.LocalDate
import kotlin.streams.toList

object DocumentDAO {

    private val documentMapByID =
            mutableMapOf<DocumentId, Document>()//TreeMap<DocumentId, acc.docModel.Document>()


    init {
        create(DocumentType.INVOICE, 1, LocalDate.now(), "bla")
        create(DocumentType.BANK_STATEMENT, 1, LocalDate.now(), "bla")
    }

    fun create(type: DocumentType, number: Int, date: LocalDate,
               description: String) {
        val d = Document(type, number, date,
                description)
        documentMapByID[d.id] = d
    }

    val all: List<Document>
        @Throws(AccException::class)
        get() = documentMapByID.values.toList()

    @Throws(AccException::class)
    fun getByRegexp(regexp: String): List<Document> {
        return documentMapByID.values.stream()
                .filter { d -> d.description.matches(regexp.toRegex()) }
                .toList()
    }

    @Throws(AccException::class)
    fun getById(id: DocumentId): Document? {
        return documentMapByID.get(id)
    }

    fun update(id: DocumentId, date: LocalDate,
               description: String) {
        val d = Document(id.type, id.number, date, description)
        documentMapByID.replace(d.id, d)
    }

    @Throws(AccException::class)
    fun delete(id: DocumentId) {
        documentMapByID.remove(id)
    }

    fun findFreeDocumentId(docType: DocumentType): Int {
        return documentMapByID.values.stream()
                .filter { it.type == docType }
                .mapToInt { it -> it.number }
                .max().orElse(0) + 1
    }

}

