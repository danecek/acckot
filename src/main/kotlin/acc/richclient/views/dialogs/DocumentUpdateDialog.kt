package acc.richclient.views.dialogs

import acc.business.Facade

class DocumentUpdateDialog : DocumentAbstractDialog(DialogMode.UPDATE) {
    override val ok = {
        Facade.updateDocument(docModel.id.value,
                docModel.date.value,
                docModel.description.value
        )

    }
}