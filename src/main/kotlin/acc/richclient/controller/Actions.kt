package acc.richclient.controller

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.DocType
import acc.model.Document
import acc.richclient.PaneTabs
import acc.richclient.dialogs.*
import acc.util.Messages
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.ActionEvent
import tornadofx.*

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

object BalanceCreateAction : AbstrAction(Messages.Vytvor_rozvahu.cm()) {

    override fun execute() {
        find<BalanceShowDialog>().openModal()
    }

}

object ShowUnpaidInvocesAction : AbstrAction(Messages.Zobraz_nezaplacene_faktury.cm()) {
    override fun execute() {
        PaneTabs.showUnpaidInvoicesPane()
    }
}

object ShowTransactionsAction : AbstrAction(Messages.Zobraz_transakce.cm()) {

    override fun execute() {
        find<TransactionsShowDialog>().openModal()
    }
}

object ShowAllTransactionsAction : AbstrAction(Messages.Zobraz_vsechny_transakce.cm()) {

    override fun execute() {
        PaneTabs.showTransactionPane()
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

object ShowAllDocumentsAction : AbstrAction(Messages.Zobraz_vsechny_doklady.cm()) {

    override fun execute() {
        PaneTabs.showDocumentPane()
    }
}

object ShowDocumentsAction : AbstrAction(Messages.Zobraz_doklady.cm()) {

    override fun execute() {
        find<DocumentsShowDialog>().openModal()
    }
}

object ShowAccountsAction : AbstrAction(Messages.Zobraz_ucty.cm()) {

    override fun enable() = PaneTabs.accountPane == null

    override fun execute() {
        PaneTabs.showAccountPane()
    }
}
