package acc.richclient.dialogs.docs

import acc.business.Facade
import acc.richclient.dialogs.DialogMode

class DocumentDeleteDialog : DocumentDialogFragment(DialogMode.DELETE) {

    override val ok: () -> Unit = {
        Facade.deleteDocument(docModel.id.value)
    }
}