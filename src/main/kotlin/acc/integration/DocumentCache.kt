package acc.integration

import acc.model.DocFilter
import acc.model.Document
import acc.model.DocId
import acc.model.DocType
import acc.util.AccException
import java.time.LocalDate

object DocumentCache : DocumentDAOInterface {

    private val documentMapByID =
            mutableMapOf<DocId, Document>()//TreeMap<DocId, acc.docModel.Document>()

    fun dataInit() {
        documentMapByID.clear()
    }

    override fun createDoc(type: DocType, number: Int, date: LocalDate,
                           description: String) {
        val d = Document(type, number, date,
                description)
        documentMapByID[d.id] = d
    }

    override val allDocs: List<Document>
        @Throws(AccException::class)
        get() = documentMapByID.values.toList()

    @Throws(AccException::class)
    override fun docsByFilter(docFilter: DocFilter?): MutableList<Document> {
        return documentMapByID.values
                .filter { d -> docFilter?.matchDoc(d)?:true }
                .toMutableList()
    }

    @Throws(AccException::class)
    override fun docById(id: DocId): Document {
        return documentMapByID[id]!!
    }

    override fun updateDoc(id: DocId, date: LocalDate,
                           description: String) {
        with (docById(id)){
            this.date = date
            this.description = description
        }
    }

    @Throws(AccException::class)
    override fun deleteDoc(id: DocId) {
        documentMapByID.remove(id)
    }

    override fun findFreeDocId(docType: DocType): Int {
        return allDocs.stream()
                .filter { it.type == docType }
                .mapToInt { it.number }
                .max().orElse(0) + 1
    }


}

