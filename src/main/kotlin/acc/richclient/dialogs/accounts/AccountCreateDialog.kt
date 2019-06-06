package acc.richclient.dialogs.accounts

import acc.business.Facade
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.AccountsView
import acc.richclient.panes.PaneTabs

class AccountCreateDialog : AccountDialogFragment(DialogMode.CREATE) {

    override val ok: () -> Unit = {
        Facade.createAccount(
                accModel.group.value,
                accModel.anal.value,
                accModel.name.value ?: "",
                accModel.initAmount.value
        )
    }
}