package acc.richclient.dialogs

import acc.business.Facade

class TransactionUpdateDialog : TransactionDialogFragment(DialogMode.UPDATE) {
        override val ok = {
        Facade.updateTransaction(transModel.id.value,
                transModel.amount.value,
                transModel.maDati.value,
                transModel.dal.value,
                transModel.document.value,
                transModel.relatedDocument.value
        )

    }
}