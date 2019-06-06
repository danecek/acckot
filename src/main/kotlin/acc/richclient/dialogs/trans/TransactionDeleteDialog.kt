package acc.richclient.dialogs.trans

import acc.business.Facade
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.PaneTabs

class TransactionDeleteDialog : TransactionDialogFragment(DialogMode.DELETE) {

    override val ok = {
        Facade.deleteTransaction(transModel.id.value)
       // PaneTabs.clearIncomeAndBalance()
    }
}