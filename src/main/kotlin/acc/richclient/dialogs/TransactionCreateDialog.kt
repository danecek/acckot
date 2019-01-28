package acc.richclient.dialogs

import acc.business.Facade

class TransactionCreateDialog : TransactionDialogFragment(DialogMode.CREATE) {
    override val ok = {
        Facade.createTransaction(transModel.amount.value,
                transModel.maDati.value,
                transModel.dal.value,
                transModel.document.value,
                transModel.relatedDocument.value
        )

    }
}