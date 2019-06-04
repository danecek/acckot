package acc.richclient.dialogs.trans

import acc.business.Facade
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.PaneTabs
import acc.richclient.panes.TransactionsView

class TransactionCreateDialog() : TransactionDialogFragment(DialogMode.CREATE) {
    override val ok = {
        Facade.createTransaction(transModel.amount.value,
                transModel.maDati.value,
                transModel.dal.value,
                transModel.document.value,
                transModel.relatedDocument.value
        )
        PaneTabs.clearIncomeAndBalance()
        PaneTabs.selectView<TransactionsView>()
    }
}
