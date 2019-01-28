package acc.richclient.dialogs

import acc.business.Facade
import acc.model.AnalId

class AccountUpdateDialog : AccountDialogFragment(DialogMode.UPDATE) {
    
    override val ok: () -> Unit = {
        Facade.updateAccount(AnalId(accModel.group.value,
                accModel.anal.value),
                accModel.name.value,
                accModel.initAmount.value
                )
    }
}