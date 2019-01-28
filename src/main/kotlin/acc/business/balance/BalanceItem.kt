package acc.business.balance

import acc.model.AbstrGroup
import java.util.*

class BalanceItem(val group: AbstrGroup? = null) {

    var initAssets: Long = 0
    var initLiabilities: Long = 0
    var periodAssets: Long = 0
    var periodLiabilities: Long = 0
    var sumAssets: Long = 0
    var sumLiabilities: Long = 0
    var finalAssets: Long = 0
    var finalLiabilities: Long = 0

    val children: MutableMap<String, BalanceItem> = HashMap()

    val name: String
        get() = group?.name ?: "SUM"


    val number: String
        get() = group?.number ?: ""


    internal fun print(intnd: Int) {
        if (intnd == 0) {
            println("********************************")
        }
        print(sp(intnd))
        println(toString())
        for (bi in children.values) {
            bi.print(intnd + 5)
        }
    }

    fun appendItemsTo(items: MutableList<BalanceItem>): List<BalanceItem> {
        children.values.forEach {
            it.appendItemsTo(items)
        }
        items.add(this)
        return items
    }

    fun sum() {
        children.values.forEach {
            it.sum()
            addInitAssets(it.initAssets)
            addInitLiabilities(it.initLiabilities)
            addAssets(it.periodAssets)
            addLiabilities(it.periodLiabilities)
            addAssetsSum(it.sumAssets)
            addLiabilitiesSum(it.sumLiabilities)
            addFinalLiabilities(it.finalLiabilities)
        }
    }

    fun addAssets(suma: Long) {
        periodAssets += suma
    }

    fun addLiabilities(suma: Long) {
        periodLiabilities += suma
    }

    fun addAssetsSum(suma: Long) {
        sumAssets += suma
    }

    fun addLiabilitiesSum(suma: Long) {
        sumLiabilities += suma
    }

    fun addInitAssets(suma: Long) {
        initAssets += suma
    }

    fun addInitLiabilities(suma: Long) {
        initLiabilities += suma
    }

    fun addFinalAssets(suma: Long) {
        finalAssets += suma
    }

    fun addFinalLiabilities(suma: Long) {
        finalLiabilities += suma
    }

    override fun toString(): String {
        return "BalanceItem{" + "group=" + group + ", periodAssets=" + periodAssets + ", periodLiabilities=" + periodLiabilities + ", sumAssets=" + sumAssets + ", sumLiabilities=" + sumLiabilities + '}'.toString()
    }

    companion object {

        internal fun sp(l: Int): String {
            val s = CharArray(l)
            Arrays.fill(s, ' ')
            return String(s)
        }
    }
}
