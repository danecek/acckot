package acc.richclient.views.dialogs

import acc.business.Facade
import acc.model.TransactionFilter
import acc.util.Messages
import javafx.scene.control.Alert
import tornadofx.*

class DocumentDeleteDialog : DocumentAbstractDialog(DialogMode.DELETE) {

    override val ok: () -> Unit = {
        Facade.deleteDocument(docModel.id.value)
    }
}