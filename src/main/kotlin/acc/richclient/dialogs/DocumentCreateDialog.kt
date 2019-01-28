package acc.richclient.dialogs

import acc.business.Facade

class DocumentCreateDialog : DocumentDialogFragment(DialogMode.CREATE) {

    override val ok: () -> Unit = {
        Facade.createDocument(docModel.type.value,
                docModel.number.value,
                docModel.date.value,
                docModel.description.value ?: ""
        )

    }
}