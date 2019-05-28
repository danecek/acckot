package acc.richclient.dialogs.trans

import acc.business.Facade
import acc.richclient.PaneTabs
import acc.richclient.dialogs.DialogMode
import acc.util.catchAccException

class TransactionDeleteDialog : TransactionDialogFragment(DialogMode.DELETE) {

    override val ok = {
        catchAccException {
            Facade.deleteTransaction(transModel.id.value)
            PaneTabs.clearIncomeBalance()
        }

    }
}