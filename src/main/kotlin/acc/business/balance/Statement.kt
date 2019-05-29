package acc.business.balance

import acc.Options
import acc.business.Facade
import acc.model.AbstrAcc
import acc.util.AccException
import acc.util.Messages
import java.time.LocalDate
import java.time.Month
import java.util.*

class Statement {

    private val itemsMap: MutableMap<AbstrAcc, StatItem> = TreeMap()
    private val treeRootItem: StatItem = StatItem()

    @Throws(AccException::class)
    fun createItemList(stat: Messages, month: Month, includeSyntAcc: Boolean): List<StatItem> {
        itemsMap.clear()
        when (stat) {
            Messages.Rozvaha ->
                Facade.balanceAccounts.forEach { acc -> insertToTree(acc) }

            Messages.Zisky_a_ztraty ->
                Facade.incomeAccounts.forEach { acc -> insertToTree(acc) }
            else -> kotlin.error(stat)
        }
        addTransactions(month)
        treeRootItem.sum()
        return treeRootItem.addNodesTo(mutableListOf(), includeSyntAcc)
    }

    private fun insertToTree(acc: AbstrAcc?): StatItem {
        if (acc == null) {
            return treeRootItem
        }
        val parentItem = insertToTree(acc.parent)
        val siblings: MutableMap<String, StatItem> = parentItem.children
        var newItem: StatItem? = siblings[acc.number]
        if (newItem == null) {
            newItem = StatItem(acc)
            itemsMap[acc] = newItem
            siblings[acc.number] = newItem
        }
        return newItem
    }

/*    private fun addInit(acc: AnalAcc) {
        val item = itemsMap[acc]!!
        if (acc.isActive) {
            item.addInitAssets(acc.initAmount)
            item.addFinalAssets(acc.initAmount)

        } else {
            item.addInitLiabilities(acc.initAmount)
            item.addFinalLiabilities(acc.initAmount)

        }
    }*/

    private fun addTrans(item: StatItem, inMonth: Boolean, amount: Long, isAssets: Boolean) {
        item.addToSum(amount)
        if (inMonth) {
            item.addToPeriod(amount)
        }
        if (isAssets) {
            item.addFinalAssets(amount)
        } else {
            item.addFinalLiabilities(amount)
        }
    }

    @Throws(AccException::class)
    private fun addTransactions(month: Month) {
        Facade.allTransactions
                .forEach { trans ->
                    val monthBegin = LocalDate.of(Options.year, month, 1)
                    val nextMontBegin = monthBegin.plusMonths(1)
                    val transDate = trans.doc.date
                    val inMonth =
                            !transDate.isBefore(monthBegin) and transDate.isBefore(nextMontBegin)
                    val maDatiAcc = trans.maDati
                    itemsMap[maDatiAcc]?.addTrans(inMonth, +trans.amount, maDatiAcc.isActive)
                    val dalAcc = trans.dal
                    itemsMap[dalAcc]?.addTrans(inMonth, -trans.amount, dalAcc.isActive)
                }

    }

}