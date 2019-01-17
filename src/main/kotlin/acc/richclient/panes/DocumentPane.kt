package acc.richclient.panes

import acc.business.Facade
import acc.model.Document
import acc.model.Transaction
import acc.model.TransactionFilter
import acc.util.Messages
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*

class DocumentPane(val content: TableView<Document>)
    : TitledPane(Messages.Doklady.cm(), content) {

    fun refresh() {
        content.items.setAll(Facade.allDocuments.observable())
    }
    
    val selected: Document?
        get() =  content.selectedItem
}