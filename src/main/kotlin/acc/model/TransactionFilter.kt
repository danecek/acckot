package acc.model

import java.time.LocalDate

class TransactionFilter(val Od: LocalDate?, val Do: LocalDate?,
                        val doc: Document?, val relatedDoc: Document?) {


    fun match(t: Transaction): Boolean {
        val date = t.document.date

        if (Od != null && date.isBefore(Od)) return false
        if (Do != null && date.isAfter(Do)) return false
        if (doc != null && t.document != doc) return false
        if (relatedDoc != null && t.relatedDocument != relatedDoc) return false
        return true

    }
}