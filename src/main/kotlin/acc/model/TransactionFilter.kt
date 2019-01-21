package acc.model

import acc.util.Global
import acc.util.Messages
import acc.util.withColon
import java.time.LocalDate

class TransactionFilter(val Od: LocalDate?=null, val Do: LocalDate?=null,
                        val doc: Document?=null, val relatedDoc: Document?=null) {

    fun match(t: Transaction): Boolean {
        val date = t.document.date

        if (Od != null && date.isBefore(Od)) return false
        if (Do != null && date.isAfter(Do)) return false
        if (doc != null && t.document.id != doc.id) return false
        if (relatedDoc != null && t.relatedDocument != relatedDoc) return false
        return true

    }

    override fun toString(): String {
       val sb= StringBuilder()
        if (Od!=null)
            sb.append(Messages.Od.cm().withColon).append(Global.df.format(Od))
        return sb.toString()
    }

}