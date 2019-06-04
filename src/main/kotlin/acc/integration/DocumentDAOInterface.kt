package acc.integration

import acc.model.DocFilter
import acc.model.DocType
import acc.model.Document
import acc.util.AccException
import java.time.LocalDate

interface DocumentDAOInterface {
    val allDocs: List<Document>
    @Throws(AccException::class)
    fun createDoc(type: DocType, number: Int, date: LocalDate,
                  description: String): Int

    @Throws(AccException::class)
    fun docsByFilter(docFilter: DocFilter?): List<Document>

    fun updateDoc(doc: Document, date: LocalDate,
                  description: String)

    @Throws(AccException::class)
    fun deleteDoc(doc: Document)

    fun findFreeDocNumber(docType: DocType): Int
}