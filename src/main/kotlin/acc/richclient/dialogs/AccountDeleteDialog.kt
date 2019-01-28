package acc.richclient.dialogs

import acc.business.Facade
import acc.model.AnalId

class AccountDeleteDialog : AccountDialogFragment(DialogMode.DELETE) {

    override val ok: () -> Unit = {
        Facade.deleteAccount(AnalId(accModel.group.value, accModel.anal.value))
    }
}