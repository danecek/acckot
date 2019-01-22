package acc.richclient.views.panes

import acc.business.Facade
import acc.model.Document
import acc.richclient.views.dialogs.DocumentDeleteDialog
import acc.richclient.views.dialogs.DocumentUpdateDialog
import acc.util.Messages
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import acc.model.TransactionFilter
import javafx.scene.control.Alert
import tornadofx.*

class DocumentView : View() {

    val tw = tableview<Document>(Facade.allDocuments.observable()) {
        column<Document, String>(Messages.Typ_dokladu.cm()) { it ->
            SimpleStringProperty(it.value.type.text)
        }
        readonlyColumn(Messages.Cislo.cm(), Document::number)
        readonlyColumn(Messages.Datum.cm(), Document::date)
        readonlyColumn(Messages.Popis.cm(), Document::description)
        contextmenu {
            item(Messages.Zmen_doklad.cm()) {
                action { find<DocumentUpdateDialog>(params = mapOf("doc" to selectedItem)).openModal() }
            }
            item(Messages.Zrus_doklad.cm()) {
                action {
                    if (Facade.getTransactions(TransactionFilter(doc = selectedItem)).isEmpty())
                        find<DocumentDeleteDialog>(params = mapOf("doc" to selectedItem)).openModal()
                    else alert(Alert.AlertType.ERROR, Messages.Doklad_je_pouzit_v_transakci.cm())
                }
            }
        }
    }

    override val root = DocumentPane(tw)

}