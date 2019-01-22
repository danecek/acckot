package acc.richclient.views.dialogs

import acc.business.Facade

class TransactionDeleteDialog : TransactionAbstractDialog(DialogMode.DELETE) {

    override val ok = {
        Facade.deleteTransaction(transModel.id.value)

    }
}