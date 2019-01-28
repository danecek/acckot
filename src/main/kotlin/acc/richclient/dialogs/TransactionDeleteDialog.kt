package acc.richclient.dialogs

import acc.business.Facade

class TransactionDeleteDialog : TransactionDialogFragment(DialogMode.DELETE) {

    override val ok = {
        Facade.deleteTransaction(transModel.id.value)

    }
}