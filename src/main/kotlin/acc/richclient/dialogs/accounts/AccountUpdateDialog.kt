package acc.richclient.dialogs.accounts

import acc.business.Facade
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.TransactionsView

class AccountUpdateDialog : AccountDialogFragment(DialogMode.UPDATE) {

    override val ok: () -> Unit = {
        Facade.updateAccount(acc!!,
                accModel.name.value,
                accModel.initAmount.value
        )
        find<TransactionsView>().update()
    }
}