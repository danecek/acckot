package acc.richclient.dialogs

import acc.business.Facade

class DocumentDeleteDialog : DocumentAbstractDialog(DialogMode.DELETE) {

    override val ok = {
        Facade.deleteDocument(model.id.value)

    }
}