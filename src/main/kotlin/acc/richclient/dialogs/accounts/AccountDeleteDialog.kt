package acc.richclient.dialogs.accounts

import acc.business.Facade
import acc.model.AnalId
import acc.richclient.dialogs.DialogMode

class AccountDeleteDialog : AccountDialogFragment(DialogMode.DELETE) {

    override val ok: () -> Unit = {
        Facade.deleteAccount(AnalId(accModel.group.value, accModel.anal.value))
    }
}