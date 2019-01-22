package acc.model

import acc.util.Global
import acc.util.Messages
import acc.util.withColon
import java.time.LocalDate

class TransactionFilter(val Od: LocalDate?=null, val Do: LocalDate?=null,
                        val account: AnalAcc? = null,
                        val doc: Document?=null) {

    fun match(t: Transaction): Boolean {
        val date = t.document.date
        if (Od != null && date.isBefore(Od)) return false
        if (Do != null && date.isAfter(Do)) return false
        if (account != null && t.maDati != account && t.dal != account) return false
        if (doc != null && t.document != doc && t.relatedDocument!=doc) return false
        return true
    }

    override fun toString(): String {
       val sb= StringBuilder()
        if (Od!=null)
            sb.append(Messages.Od.cm().withColon).append(Global.df.format(Od))
        return sb.toString()
    }

}