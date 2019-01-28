package acc.model

import acc.util.dayMonthFrm
import acc.util.Messages
import acc.util.withColon
import java.time.LocalDate

const val COMMA_DEL = ",  "

abstract class AbstrFilter(val from: LocalDate?, val tto: LocalDate?) {

    fun matchDate(date:LocalDate): Boolean {
        if (from != null && date.isBefore(from)) return false
        if (tto != null && date.isAfter(tto)) return false
        return true
    }

    fun elems(): MutableList<String> {
        val buf = ArrayList<String>()
        if (from != null)
            buf.add("${Messages.Od.cm().withColon}${dayMonthFrm.format(from)}")
        if (tto != null)
            buf.add("${Messages.Do.cm().withColon}${dayMonthFrm.format(tto)}")
        return buf
    }

}