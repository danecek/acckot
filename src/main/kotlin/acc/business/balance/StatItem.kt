package acc.business.balance

import acc.model.AbstrAcc
import acc.model.AnalAcc
import java.util.*

class StatItem(val acc: AbstrAcc? = null) {

    var order:Int=0
    var initAssets: Long = 0
    var initLiabilities: Long = 0
    var periodDebits: Long = 0
    var periodCredits: Long = 0
    var sumDebits: Long = 0
    var sumCredits: Long = 0
    var finalAssets: Long = 0
    var finalLiabilities: Long = 0

    init {
        if (acc is AnalAcc && acc.isBalanced) {
            if (acc !!.isActive) {
                addInitAssets(acc.initAmount)
                addFinalAssets(acc.initAmount)

            } else {
                addInitLiabilities(acc.initAmount)
                addFinalLiabilities(acc.initAmount)

            }
        }
    }

    val children: MutableMap<String, StatItem> = HashMap()

    val name: String
        get() = acc?.name ?: "SUM"

    val number: String
        get() = acc?.number ?: ""

    private fun print(intnd: Int) {
        if (intnd == 0) {
            println("********************************")
        }
        print(" ".repeat(intnd))
        println(this)
        for (bi in children.values) {
            bi.print(intnd + 5)
        }
    }

    fun addNodesTo(items: MutableList<StatItem>, includeSyntAcc: Boolean): List<StatItem> {
        children.forEach {
            it.value.addNodesTo(items, includeSyntAcc)
        }
        if (acc == null || acc is AnalAcc || includeSyntAcc) {
            order = items.size+1
            items.add(this)
        }
        return items
    }

    fun sum() {
        children.values.forEach {
            it.sum()
            initAssets += it.initAssets
            initLiabilities += it.initLiabilities
            periodDebits += it.periodDebits
            periodCredits += it.periodCredits
            sumDebits += it.sumDebits
            sumCredits += it.sumCredits
            finalAssets += it.finalAssets
            finalLiabilities += it.finalLiabilities
        }
    }

    fun addToPeriod(amount: Long) {
        if (amount > 0)
            periodDebits += amount
        else
            periodCredits -= amount
    }

    fun addToSum(amount: Long) {
        if (amount > 0)
            sumDebits += amount
        else
            sumCredits -= amount
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

    fun addTrans(inMonth: Boolean, amount: Long, isAssets:Boolean) {
        addToSum(amount)
        if (inMonth) {
            addToPeriod(amount)
        }
        if (isAssets) {
            addFinalAssets(amount)
        } else {
            addFinalLiabilities(amount)
        }
    }

    override fun toString(): String {
        return "StatItem{group=$acc, periodDebits=$periodDebits, periodCredits=$periodCredits, sumDebits=$sumDebits, sumCredits=$sumCredits}"
    }
}
