package acc.richclient.views.dialogs

import acc.business.Facade

class InitDeleteDialog : InitAbstractDialog(DialogMode.DELETE) {
    override val ok = {
        Facade.deleteTransaction(model.id.value)

    }

}