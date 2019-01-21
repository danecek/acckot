package acc.richclient.views.dialogs

import acc.business.Facade

class InitCreateDialog : InitAbstractDialog(DialogMode.CREATE) {
    override val ok = {
        Facade.createInit(model.amount.value,
                model.maDati.value,
                model.madatiDal.value)

    }

}