package acc.model

import acc.util.Messages
import acc.util.withColon
import java.time.LocalDate

class TransactionFilter(from: LocalDate? = null, tto: LocalDate? = null,
                        val acc: AnalAcc? = null,
                        val doc: Document? = null) : AbstrFilter(from, tto) {

    fun match(t: Transaction): Boolean {
        if (!matchDate(t.doc.date)) {
            println("!dateMatch")
            return false
        }
        if (acc != null && t.maDati != acc && t.dal != acc) {
            println("$acc  && ${t.maDati}  && ${t.dal}")
            return false
        }
        if (doc != null && t.doc != doc && t.relatedDoc != doc) {
            println("$doc  && $t.doc && $t.relatedDoc")
            return false
        }
        return true
    }

    override fun toString(): String {
        val buf = super.elems()
        if (acc != null) {
            buf.add("${Messages.S_uctem.cm().withColon}${acc.numberName()}")
        }
        if (doc != null) {
            buf.add("${Messages.S_dokladem.cm().withColon}${doc.name}")
        }
        return if (buf.isEmpty()) Messages.Vsechny_transakce.cm()
        else buf.joinToString(separator = COMMA_DEL)
    }

}