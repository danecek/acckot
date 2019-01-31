package acc.richclient.panes

import acc.business.Facade
import acc.model.DocFilter
import acc.model.Document
import acc.util.Messages
import javafx.scene.Node
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*

class DocumentPane(val df: DocFilter?, val content : TableView<Document>)
    : TitledPane(df?.toString() ?: Messages.Vsechny.cm(), content) {

    fun refresh() {
        runAsync {
            Facade.documentsByFilter(df)
        } fail {
            error(it)
        } ui {
            content.items.setAll(it)
        }

    }

}