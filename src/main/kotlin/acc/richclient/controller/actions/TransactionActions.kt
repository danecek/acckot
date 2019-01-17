package acc.richclient.controller.actions

import acc.richclient.dialogs.TransactionCreateDialog
import acc.richclient.dialogs.TransactionDeleteDialog
import acc.richclient.dialogs.TransactionUpdateDialog
import acc.richclient.dialogs.TransactionsShowDialog
import acc.richclient.views.PaneTabs
import acc.util.Messages
import javafx.stage.StageStyle
import tornadofx.*

object TransactionsShowAction : AbstrAction() {
    override val name = Messages.Zobraz_transakce.cm()
    override fun execute() {
        find<TransactionsShowDialog>().openModal(StageStyle.DECORATED)
    }
}

object TransactionCreateAction : AbstrAction() {

    override val name = Messages.Vytvor_transakci.cm()

    override fun execute() {
        find<TransactionCreateDialog>().openModal(StageStyle.DECORATED)
    }
}


object TransactionUpdateAction : AbstrAction() {

    override val name = Messages.Zmen_transakci.cm()

    override fun execute() {
        val tr = PaneTabs.selectedTransaction
        find<TransactionUpdateDialog>(params = mapOf("tr" to tr)).openModal()
    }


}

object TransactionDeleteAction : AbstrAction() {

    override val name = Messages.Zrus_transakci.cm()

    @Override
    override fun execute() {
        val tr = PaneTabs.selectedTransaction
        find<TransactionDeleteDialog>(params = mapOf("tr" to tr)).openModal()
    }
}

object TransactionCreateByInvoiceAction : AbstrAction() {
    override val name = Messages.Vytvor_transakci.cm()

    override fun execute() {
        /*       MainWindow.instance.getSelectedTab(Messages.Doklady.cm())
                       .ifPresent {
                           it.selected
                                   .ifPresent { d -> TransactionCreateDialog(d as Document).execute() }
                       }*/
    }
}




