package acc.model


import acc.util.Messages
import acc.util.withColon
import java.time.LocalDate

open class DocFilter(
        val types: Set<String> = emptySet(),
        from: LocalDate? = null, tto: LocalDate? = null) : AbstrFilter(from, tto) {

    fun matchDoc(doc: Document): Boolean {
        if (!matchDate(doc.date)) return false;
        if (!types.contains(doc.type.abbr)) return false
        return true
    }

    override fun toString(): String {
        val els = mutableListOf<Any>()
        if (types.size == DocType.values().size)
            els.add(Messages.Vsechny_typy.cm())
        else
            els.add(Messages.Typy.cm().withColon.plus(types))
        els.addAll(super.elems())
        return els.joinToString(separator = COMMA_DEL)
    }

}

object UnpaidInvoicesFilter : DocFilter() {
    override fun toString(): String {
        return Messages.Nezaplacene_faktury.cm()
    }

}
