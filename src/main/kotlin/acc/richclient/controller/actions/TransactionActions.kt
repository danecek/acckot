package acc.richclient.controller.actions

import acc.model.Document
import acc.richclient.MainWindow
import acc.richclient.dialogs.TransactionCreateDialog
import acc.richclient.dialogs.TransactionDeleteDialog
import acc.richclient.dialogs.TransactionUpdateDialog
import acc.util.AccException
import acc.util.Messages
import acc.richclient.views.PaneTabs


object TransactionCreateAction : AbstrAction() {
    override val name = Messages.Vytvor_transakci.cm()
    override fun execute() {
        TransactionCreateDialog().execute()
    }
}

/*
object TransactionsShowAction : AbstrAction() {
    override val number = Messages.Zobraz_transakce.cm()
    override fun execute() {
        PaneTabs.addTab(Messages.Ucty.cm(), AccountsPane());
    }
}
*/


object TransactionCreateByInvoiceAction : AbstrAction() {
    override val name = Messages.Vytvor_transakci.cm()

    override fun execute() {
        MainWindow.instance.getSelectedTab(Messages.Doklady.cm())
                .ifPresent {
                    it.selected
                            .ifPresent { d -> TransactionCreateDialog(d as Document).execute() }
                }
    }
}


object TransactionDeleteAction : AbstrAction() {

    override val name = Messages.Zrus_transakci.cm()

    @Override
    override fun execute() {
        MainWindow.getInstance().selectedTransactionPane
                .ifPresent {
                    it.selected
                            .ifPresent {
                                try {
                                    TransactionDeleteDialog(it).execute();
                                } catch (ex: AccException) {
                                    MainWindow.showException(ex);
                                }
                            }

                }
    }
}

object UpdateTransactionAction : AbstrAction() {

    override val name = Messages.Zmen_transakci.cm()

    override fun execute() {
        MainWindow.getInstance().selectedTransactionPane
                .ifPresent { tp ->
                    tp.selected
                            .ifPresent { t ->
                                try {
                                    TransactionUpdateDialog(t).execute()
                                } catch (ex: AccException) {
                                    MainWindow.showException(ex)
                                }
                            }
                }
    }


}