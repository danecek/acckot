package acc.richclient.dialogs

import acc.business.Facade

class TransactionUpdateDialog : TransactionAbstractDialog(DialogMode.UPDATE) {
        override val ok = {
        Facade.updateTransaction(model.id.value,
                model.amount.value,
                model.maDati.value,
                model.dal.value,
                model.document.value,
                model.relatedDocument.value
        )

    }
}