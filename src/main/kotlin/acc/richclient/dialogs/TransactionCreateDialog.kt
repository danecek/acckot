package acc.richclient.dialogs

import acc.business.Facade

class TransactionCreateDialog : TransactionAbstractDialog(DialogMode.CREATE) {
    override val ok = {
        Facade.createTransaction(model.amount.value,
                model.maDati.value,
                model.dal.value,
                model.document.value,
                model.relatedDocument.value
        )

    }
}