package acc.model

import acc.business.Facade
import acc.util.Messages
import acc.util.withColon
import java.time.LocalDate

class TransactionFilter(from: LocalDate?=null, tto: LocalDate?=null,
                        val acc: AnalAcc? = null,
                        val doc: Document?=null) : AbstrFilter(from, tto) {

    fun match(t: Transaction): Boolean {
        if (!matchDate(t.doc.date)) return false
        if (acc != null && t.maDati != acc && t.dal != acc) return false
        if (doc != null && t.doc != doc && t.relatedDoc!=doc) return false
        return true
    }

    override fun toString(): String {
        val buf = super.elems()
        if (acc!=null) {
            buf.add("${Messages.S_uctem.cm().withColon}${acc.numberName}")
        }
        if (doc!=null) {
            buf.add("${Messages.S_dokladem.cm().withColon}${doc.id}")
        }
        return if (buf.isEmpty()) Messages.Vsechny.cm()
        else buf.joinToString (separator =  COMMA_DEL)
    }

}