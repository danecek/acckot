package acc.richclient.dialogs.trans

import acc.business.Facade
import acc.richclient.PaneTabs
import acc.richclient.dialogs.DialogMode
import acc.util.catchAccException

class TransactionCreateDialog : TransactionDialogFragment(DialogMode.CREATE) {
    override val ok = {
        catchAccException {
            PaneTabs.clearIncomeBalance()
            Facade.createTransaction(transModel.amount.value,
                    transModel.maDatiWA.value.acc!!, // workaround
                    transModel.dalWA.value.acc!!, // workaround
                    //           transModel.maDati.value,
                    //           transModel.dal.value,
                    transModel.document.value,
                    transModel.relatedDocument.value
            )

        }
    }
}