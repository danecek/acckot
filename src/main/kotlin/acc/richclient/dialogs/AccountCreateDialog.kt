package acc.richclient.dialogs

import acc.business.Facade
import acc.util.AccException
import javafx.application.Platform
import javafx.scene.control.Alert
import tornadofx.*

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