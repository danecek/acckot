package acc.richclient.views.panes

import acc.business.Facade
import acc.model.Document
import acc.util.Messages
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*

class DocumentPane(private val content: TableView<Document>)
    : TitledPane(Messages.Doklady.cm(), content) {

    fun refresh() {
        content.items.setAll(Facade.allDocuments.observable())
    }

}