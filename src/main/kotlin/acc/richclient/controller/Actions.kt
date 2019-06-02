package acc.richclient.controller

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.DocType
import acc.model.Document
import acc.richclient.panes.PaneTabs
import acc.richclient.dialogs.BalanceShowDialog
import acc.richclient.dialogs.accounts.AccountDeleteDialog
import acc.richclient.dialogs.accounts.AccountUpdateDialog
import acc.richclient.dialogs.docs.DocumentCreateDialog
import acc.richclient.dialogs.docs.DocumentsShowDialog
import acc.richclient.dialogs.trans.TransactionCreateDialog
import acc.richclient.dialogs.trans.TransactionsFilterDialog
import acc.richclient.panes.DocumentsView
import acc.richclient.panes.TransactionsView
import acc.util.Messages
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

fun openTransactionCreateDialog(doc: Document? = null) {
    find<TransactionCreateDialog>(
            params = mapOf("doc" to doc)).openModal()
}

fun openDocCreateDialog(docType: DocType) {
    val id = Facade.genDocumentId(docType)
    find<DocumentCreateDialog>(params = mapOf("docType" to docType, "number" to id)).openModal()
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

object ShowUnpaidInvocesAction : AbstrAction(Messages.Nezaplacene_faktury.cm()) {
    override fun execute() {
        PaneTabs.showUnpaidInvoicesPane()
    }
}

object OpenTransFilterDialogAction : AbstrAction(Messages.Filter_transakci.cm()) {

    override fun execute() {
        find<TransactionsFilterDialog>().openModal()
    }
}

object ClearTransFilterAction : AbstrAction(Messages.Vsechny_transakce.cm()) {

    override fun execute() {
        find<TransactionsView>().transFilter = null
    }
}

object TransactionCreateAction : AbstrAction(Messages.Vytvor_transakci.cm()) {

    override fun execute() {
        openTransactionCreateDialog()
    }
}


object PrintBalanceAction : AbstrAction(Messages.Tisk_rozvahy.cm()) {
    override fun execute() {

    }
}

object ClearDocFilterAction : AbstrAction(Messages.Vsechny_doklady.cm()) {

    override fun execute() {
        find<DocumentsView>().docFilter = null
    }
}

object OpenDocFilterDialogAction : AbstrAction(Messages.Filter_dokladu.cm()) {

    override fun execute() {
        find<DocumentsShowDialog>().openModal()
    }
}

