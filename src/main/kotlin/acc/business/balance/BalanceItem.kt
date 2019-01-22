package acc.business.balance

import acc.model.AbstrGroup
import java.util.*

class BalanceItem(val group: AbstrGroup?=null) {

    var initAssets: Long = 0
    var initLiabilities: Long = 0
    var assets: Long = 0
    var liabilities: Long = 0
    var assetsSum: Long = 0
    var liabilitiesSum: Long = 0
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
        children.forEach { k, v -> v.appendItemsTo(items) }
        items.add(this)
        return items
    }

    fun sum() {
        children.forEach { k, v ->
            v.sum()
            addInitAssets(v.initAssets)
            addInitLiabilities(v.initLiabilities)
            addAssets(v.assets)
            addLiabilities(v.liabilities)
            addAssetsSum(v.assetsSum)
            addLiabilitiesSum(v.liabilitiesSum)
            addFinalLiabilities(v.finalLiabilities)
        }
    }

    fun addAssets(suma: Long) {
        assets += suma
    }

    fun addLiabilities(suma: Long) {
        liabilities += suma
    }

    fun addAssetsSum(suma: Long) {
        assetsSum += suma
    }

    fun addLiabilitiesSum(suma: Long) {
        liabilitiesSum += suma
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
        return "BalanceItem{" + "group=" + group + ", assets=" + assets + ", liabilities=" + liabilities + ", assetsSum=" + assetsSum + ", liabilitiesSum=" + liabilitiesSum + '}'.toString()
    }

    companion object {

        internal fun sp(l: Int): String {
             val s = CharArray(l)
            Arrays.fill(s, ' ')
            return String(s)
        }
    }
}
