package acc.richclient.dialogs.trans

import acc.business.Facade
import acc.richclient.dialogs.DialogMode

class TransactionDeleteDialog : TransactionDialogFragment(DialogMode.DELETE) {

    override val ok = {
        Facade.deleteTransaction(transModel.id.value)

    }
}