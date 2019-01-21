package acc.business.balance

import acc.business.Facade
import acc.model.AbstrGroup
import acc.model.AnalAcc
import acc.util.AccException
import acc.util.Global
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

    fun addInit(acc: AnalAcc, amount: Long) {
        if (!acc.balanced) {
            return
        }
        val item = bitems[acc]!!
        if (acc.isActive) {
            item.addInitAssets(amount)
            item.addFinalAssets(amount)

        } else {
            item.addInitLiabilities(amount)
            item.addFinalLiabilities(amount)

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

        Facade.getInits().forEach {
            addInit(it.maDati, it.amount)
            addInit(it.dal, -it.amount)
        }
        Facade.allTransactions.stream()
                .forEach { trans ->
                    val monthBegin = LocalDate.of(Global.year, month, 1)
                    val nextMontBegin = monthBegin.plusMonths(1)
                    val transDate = trans.document.date
                    val inMonth =
                            !transDate.isBefore(monthBegin) and transDate.isBefore(nextMontBegin)
                    if (trans.maDati.balanced)
                        addTrans(trans.maDati, inMonth, +trans.amount)
                    if (trans.dal.balanced)
                        addTrans(trans.dal, inMonth, -trans.amount)
                }

    }

}