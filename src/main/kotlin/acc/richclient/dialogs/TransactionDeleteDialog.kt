package acc.richclient.dialogs

import acc.business.Facade

class TransactionDeleteDialog : TransactionAbstractDialog(DialogMode.DELETE) {

    override val ok = {
        Facade.deleteTransaction(model.id.value)

    }
}