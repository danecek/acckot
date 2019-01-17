package acc.richclient.dialogs

import acc.business.Facade

class DocumentUpdateDialog : DocumentAbstractDialog(DialogMode.CREATE) {
    override val ok = {
        Facade.updateDocument(model.id.value,
                  model.date.value,
                model.description.value
        )

    }
}