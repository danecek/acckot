package acc.richclient.dialogs.docs

import acc.business.Facade
import acc.richclient.dialogs.DialogMode

class DocumentCreateDialog : DocumentDialogFragment(DialogMode.CREATE) {

    override val ok: () -> Unit = {
        Facade.createDocument(docModel.type.value,
                docModel.number.value,
                docModel.date.value,
                docModel.description.value ?: ""
        )

    }
}