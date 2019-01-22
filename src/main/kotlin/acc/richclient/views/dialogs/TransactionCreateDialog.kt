package acc.richclient.views.dialogs

import acc.business.Facade

class TransactionCreateDialog : TransactionAbstractDialog(DialogMode.CREATE) {
    override val ok = {
        Facade.createTransaction(transModel.amount.value,
                transModel.maDati.value,
                transModel.dal.value,
                transModel.document.value,
                transModel.relatedDocument.value
        )

    }
}