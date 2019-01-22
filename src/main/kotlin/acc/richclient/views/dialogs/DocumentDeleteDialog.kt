package acc.richclient.views.dialogs

import acc.business.Facade
import acc.model.TransactionFilter
import acc.util.Messages
import javafx.scene.control.Alert
import tornadofx.*

class DocumentDeleteDialog : DocumentAbstractDialog(DialogMode.DELETE) {

    override val ok:()->Unit =
            {
        if (Facade.getTransactions(TransactionFilter(doc = docModel.item)).isEmpty())
            Facade.deleteDocument(docModel.id.value)
        else {
           alert(Alert.AlertType.ERROR, Messages.Doklad_je_pouzit_v_transakci.cm())
        }

    }
}