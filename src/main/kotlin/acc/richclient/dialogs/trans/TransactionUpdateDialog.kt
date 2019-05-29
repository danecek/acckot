package acc.richclient.dialogs.trans

import acc.business.Facade
import acc.richclient.PaneTabs
import acc.richclient.dialogs.DialogMode
import acc.util.catchAccException

class TransactionUpdateDialog : TransactionDialogFragment(DialogMode.UPDATE) {
    override val ok = {
        catchAccException {
            Facade.updateTransaction(transModel.id.value,
                    transModel.amount.value,
                    transModel.maDatiWA.value.acc!!, // workaround
                    transModel.dalWA.value.acc!!,// workaround
                    transModel.document.value,
                    transModel.relatedDocument.value
            )
            PaneTabs.clearIncomeBalance()
        }
    }
}