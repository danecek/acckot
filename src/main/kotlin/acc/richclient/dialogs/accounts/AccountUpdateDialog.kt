package acc.richclient.dialogs.accounts

import acc.business.Facade
import acc.model.AnalId
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.TransactionsView

class AccountUpdateDialog : AccountDialogFragment(DialogMode.UPDATE) {
    
    override val ok: () -> Unit = {
        Facade.updateAccount(AnalId(accModel.group.value,
                accModel.anal.value),
                accModel.name.value,
                accModel.initAmount.value
                )
        find<TransactionsView>().update()
    }
}