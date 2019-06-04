package acc.richclient.dialogs.docs

import acc.business.Facade
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.TransactionsView

class DocumentUpdateDialog : DocumentDialogFragment(DialogMode.UPDATE) {
    override val ok = {
        Facade.updateDocument(doc!!,
                docModel.date.value,
                docModel.description.value
        )
        find<TransactionsView>().update()

    }
}