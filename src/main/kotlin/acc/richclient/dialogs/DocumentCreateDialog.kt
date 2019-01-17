package acc.richclient.dialogs

import acc.business.Facade

class DocumentCreateDialog : DocumentAbstractDialog(DialogMode.CREATE) {
    override val ok = {
        Facade.createDocument(model.type.value,
                model.date.value,
                model.number.value,
                model.description.value

        )

    }
}