package acc.richclient.views.dialogs

import acc.business.Facade

class AccountDeleteDialog : AccountAbstractDialog(DialogMode.DELETE) {

    override val ok: () -> Unit = {
        Facade.deleteAccount(model.id.value)
    }
}