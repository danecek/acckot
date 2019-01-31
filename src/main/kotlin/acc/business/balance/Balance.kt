package acc.business.balance

import acc.business.Facade
import acc.model.AbstrGroup
import acc.model.AnalAcc
import acc.util.AccException
import acc.Options
import java.time.LocalDate
import java.time.Month
import java.util.*

class Balance {

    private val bitems: MutableMap<AbstrGroup, BalanceItem> = TreeMap()
    private val root: BalanceItem = BalanceItem()

    @Throws(AccException::class)
    fun createBalance(month: Month): List<BalanceItem> {
        bitems.clear()
        Facade.balanceAccounts
                .forEach { ba -> insertToTree(ba) }
        sumTransactions(month)
        root.sum()
        return root.appendItemsTo(ArrayList())
    }

    private fun insertToTree(insertedItemGroup: AbstrGroup?): BalanceItem {
        if (insertedItemGroup == null) {
            return root
        }
        val parentItem = insertToTree(insertedItemGroup.parent)
        val siblings: MutableMap<String, BalanceItem> = parentItem.children
        var newItem: BalanceItem? = siblings[insertedItemGroup.number]
        if (newItem == null) {
            newItem = BalanceItem(insertedItemGroup)
            bitems[insertedItemGroup] = newItem
            siblings[insertedItemGroup.number] = newItem
        }
        return newItem
    }

    private fun addInit(acc: AnalAcc) {
        val item = bitems[acc]!!
      //  println(acc.initAmount)
        if (acc.isActive) {
            item.addInitAssets(acc.initAmount)
            item.addFinalAssets(acc.initAmount)

        } else {
            item.addInitLiabilities(acc.initAmount)
            item.addFinalLiabilities(acc.initAmount)

        }
    }

    private fun addTrans(acc: AnalAcc, inMonth: Boolean, amount: Long) {
        val item = bitems[acc]!!
        if (acc.isActive) {
            item.addAssetsSum(amount)
            item.addFinalAssets(amount)
            if (inMonth) {
                item.addAssets(amount)
            }
        } else
            item.addLiabilitiesSum(amount)
        item.addFinalLiabilities(amount)
        if (inMonth) {
            item.addLiabilities(amount)
        }

    }

    @Throws(AccException::class)
    private fun sumTransactions(month: Month) {
        Facade.balanceAccounts.forEach {
            addInit(it)
        }
        Facade.allTransactions.stream()
                .forEach { trans ->
                    val monthBegin = LocalDate.of(Options.year, month, 1)
                    val nextMontBegin = monthBegin.plusMonths(1)
                    val transDate = trans.doc.date
                    val inMonth =
                            !transDate.isBefore(monthBegin) and transDate.isBefore(nextMontBegin)
                    val maDati = trans.maDati
                    if (maDati.balanced)
                        addTrans(maDati, inMonth, +trans.amount)
                    if (trans.dal.balanced)
                        addTrans(trans.dal, inMonth, -trans.amount)
                }

    }

}