package acc.model


import acc.util.Messages
import acc.util.withColon
import java.time.LocalDate

open class DocFilter(
        private val types: Set<DocType> = emptySet(),
        from: LocalDate? = null, tto: LocalDate? = null) : AbstrFilter(from, tto) {

    fun matchDoc(doc: Document): Boolean {
        if (!matchDate(doc.date)) return false
        if (!types.contains(doc.type)) return false
        return true
    }

    override fun toString(): String {
        val els = mutableListOf<Any>()
        if (types.size == DocType.values().size)
            els.add(Messages.Vsechny_typy.cm())
        else
            els.add(Messages.Typy.cm().withColon.plus(types.map { it.abbr }))
        els.addAll(super.elems())
        return els.joinToString(separator = COMMA_DEL)
    }

}

object UnpaidInvoicesFilter : DocFilter() {
    override fun toString(): String {
        return Messages.Nezaplacene_faktury.cm()
    }
}
object FullFilter : DocFilter(){//types = DocType.values().toSet()) {
    override fun toString(): String {
        return Messages.Vsechny_doklady.cm()
    }

}
