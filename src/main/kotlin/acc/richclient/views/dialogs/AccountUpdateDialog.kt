package acc.richclient.views.dialogs

import acc.business.Facade

class AccountUpdateDialog : AccountAbstractDialog(DialogMode.UPDATE) {
    
    override val ok: () -> Unit = {
        Facade.updateAccount(model.group.value, model.anal.value, model.name.value)
    }
}