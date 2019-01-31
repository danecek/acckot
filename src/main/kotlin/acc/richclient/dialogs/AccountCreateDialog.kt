package acc.richclient.dialogs

import acc.business.Facade

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