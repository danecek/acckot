package acc.richclient.panes

import acc.business.Facade
import acc.model.DocFilter
import acc.model.Document
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane

class DocumentPane(private val content: TableView<Document>, val df: DocFilter)
    : TitledPane(df.toString(), content) {

    fun refresh() {
        content.items.setAll(Facade.documentsByFilter(df))
    }

}