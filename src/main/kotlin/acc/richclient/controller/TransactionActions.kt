package acc.richclient.controller

import acc.richclient.dialogs.TransactionCreateDialog
import acc.richclient.dialogs.TransactionsShowDialog
import acc.util.Messages
import tornadofx.*

object TransactionsShowAction : AbstrAction() {
    override val name = Messages.Zobraz_transakce.cm()
    override fun execute() {
        find<TransactionsShowDialog>().openModal()
    }
}


object TransactionCreateAction : AbstrAction() {

    override val name = Messages.Vytvor_transakci.cm()

    override fun execute() {
        find<TransactionCreateDialog>().openModal()
    }
}




