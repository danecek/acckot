package acc.richclient.dialogs

import acc.business.Facade

class DocumentUpdateDialog : DocumentDialogFragment(DialogMode.UPDATE) {
    override val ok = {
        Facade.updateDocument(docModel.id.value,
                docModel.date.value,
                docModel.description.value
        )

    }
}