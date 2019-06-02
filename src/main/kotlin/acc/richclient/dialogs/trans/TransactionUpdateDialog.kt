package acc.richclient.dialogs.trans

import acc.business.Facade
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.PaneTabs

class TransactionUpdateDialog : TransactionDialogFragment(DialogMode.UPDATE) {
    override val ok = {
        Facade.updateTransaction(transModel.id.value,
                transModel.amount.value,
                transModel.maDati.value, // workaround
                transModel.dal.value,// workaround
                transModel.document.value,
                transModel.relatedDocument.value
        )
        PaneTabs.clearIncomeAndBalance()
    }
}