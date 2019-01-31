package acc.integration

import acc.model.DocFilter
import acc.model.DocId
import acc.model.DocType
import acc.model.Document
import acc.util.AccException
import java.time.LocalDate

interface DocumentDAOInterface {
    val allDocs: List<Document>
    fun createDoc(type: DocType, number: Int, date: LocalDate,
                  description: String)

    @Throws(AccException::class)
    fun docsByFilter(docFilter: DocFilter?): List<Document>

    @Throws(AccException::class)
    fun docById(id: DocId): Document?

    fun updateDoc(id: DocId, date: LocalDate,
                  description: String)

    @Throws(AccException::class)
    fun deleteDoc(id: DocId)

    fun findFreeDocId(docType: DocType): Int
}