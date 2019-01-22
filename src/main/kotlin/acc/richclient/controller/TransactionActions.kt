package acc.richclient.controller

import acc.richclient.views.dialogs.TransactionCreateDialog
import acc.richclient.views.dialogs.TransactionDeleteDialog
import acc.richclient.views.dialogs.TransactionUpdateDialog
import acc.richclient.views.dialogs.TransactionsShowDialog
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

/*
object TransactionCreateAction : AbstrAction() {

    override val name = Messages.Vytvor_transakci.cm()

    override fun execute() {
        find<TransactionCreateDialog>().openModal(StageStyle.DECORATED)
    }
}
*/

/*

object TransactionCreateByInvoiceAction : AbstrAction() {
    override val name = Messages.Vytvor_transakci.cm()

    override fun execute() {
        */
/*       MainWindow.instance.getSelectedTab(Messages.Doklady.cm())
                       .ifPresent {
                           it.selected
                                   .ifPresent { d -> TransactionCreateDialog(d as Document).execute() }
                       }*//*

    }
}
*/




