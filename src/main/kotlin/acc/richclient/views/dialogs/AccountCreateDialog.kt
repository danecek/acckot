package acc.richclient.views.dialogs

import acc.business.Facade

class AccountCreateDialog : AccountAbstractDialog(DialogMode.CREATE) {

    override val ok: () -> Unit = {
        Facade.createAccount(model.group.value, model.anal.value, model.name.value?:"")
    }
}