package acc.richclient.controller

import acc.model.AnalAcc
import acc.model.DocType
import acc.model.Document
import acc.richclient.dialogs.BalanceShowDialog
import acc.richclient.dialogs.accounts.AccountDeleteDialog
import acc.richclient.dialogs.accounts.AccountUpdateDialog
import acc.richclient.dialogs.docs.DocumentCreateDialog
import acc.richclient.dialogs.docs.DocumentsFilterDialog
import acc.richclient.dialogs.trans.TransactionCreateDialog
import acc.richclient.dialogs.trans.TransactionsFilterDialog
import acc.richclient.panes.DocumentsView
import acc.richclient.panes.PaneTabs
import acc.richclient.panes.TransactionsView
import acc.util.Messages
import acc.util.accError
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import tornadofx.*

fun ContextMenu.add(a: AbstrAction) {
    val mi = MenuItem(a.name)
    mi.onAction = EventHandler(a)
    items.add(mi)
}

fun openTransactionCreateDialog(doc: Document) {
    find<TransactionCreateDialog>(
            params = mapOf("doc" to doc)).openModal()
}

fun openDocCreateDialog(docType: DocType) {
    find<DocumentCreateDialog>(params = mapOf("docType" to docType)).openModal()
}

fun openAccountUpdateDialog(acc: AnalAcc) {
    find<AccountUpdateDialog>(params = mapOf(AnalAcc::class.simpleName to acc)).openModal()
}

fun openAccountDeleteDialog(acc: AnalAcc) {
    find<AccountDeleteDialog>(params = mapOf(AnalAcc::class.simpleName to acc)).openModal()
}

abstract class AbstrAction(name: String) : SimpleBooleanProperty(null, name, true),
        (ActionEvent) -> Unit, InvalidationListener {

    override fun invoke(p1: ActionEvent) {
        execute()
    }

    override fun invalidated(observable: Observable?) {
        value = enable()
    }

    abstract fun execute()
    open fun enable(): Boolean = true
}

open class SheetAction(val m: Messages) : AbstrAction(m.cm()) {
    override fun execute() {
        find<BalanceShowDialog>(params = mapOf("sheet" to m)).openModal()
    }
}

object BalanceCreateAction : SheetAction(Messages.Rozvaha)

object IncomeCreateAction : SheetAction(Messages.Zisky_a_ztraty)

object ShowUnpaidInvocesAction : AbstrAction(Messages.Zobraz_nezaplacene_faktury.cm()) {
    override fun execute() {
        PaneTabs.showUnpaidInvoicesPane()
    }
}

object OpenTransFilterDialogAction : AbstrAction(Messages.Nastav_filter_transakci.cm()) {

    override fun execute() {
        find<TransactionsFilterDialog>().openModal()
    }
}

object ClearTransFilterAction : AbstrAction(Messages.Zobraz_vsechny_transakce.cm()) {

    override fun execute() {
        find<TransactionsView>().transFilter = null
        PaneTabs.selectView<TransactionsView>()
    }
}

object TransactionCreateAction : AbstrAction(Messages.Vytvor_transakci_pro_vybrany_doklad.cm()) {

    override fun execute() {
        val selDoc = find<DocumentsView>().tableView.selectedItem
        if (selDoc == null)
            accError(Messages.Neni_vyberan_zadny_doklad.cm())
        else
            openTransactionCreateDialog(doc = selDoc)
    }
}


object PrintBalanceAction : AbstrAction(Messages.Tisk_rozvahy.cm()) {
    override fun execute() {

    }
}

object ClearDocFilterAction : AbstrAction(Messages.Zobraz_vsechny_doklady.cm()) {

    override fun execute() {
        find<DocumentsView>().docFilter = null
        PaneTabs.selectView<DocumentsView>()
    }
}

object OpenDocFilterDialogAction : AbstrAction(Messages.Nastav_filter_dokladu.cm()) {

    override fun execute() {
        find<DocumentsFilterDialog>().openModal()
    }
}

