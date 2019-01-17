package acc.richclient.panes

import acc.business.Facade
import acc.model.Document
import acc.richclient.controller.actions.AccountDeleteAction
import acc.util.Messages
import javafx.event.EventHandler
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.input.MouseButton
import javafx.util.Callback
import tornadofx.*

class DocumentView : View() {

    val selected: Document?
        get() = (root.content as TableView<Document>).selectedItem

    override val root = titledpane {

        title = Messages.Doklady.cm()

        val tw = tableview<Document>(Facade.allDocuments.observable()) {
            readonlyColumn(Messages.Typ_dokladu.cm(), Document::type)
            readonlyColumn(Messages.Cislo.cm(), Document::number)
            readonlyColumn(Messages.Datum.cm(), Document::date)
            readonlyColumn(Messages.Popis.cm(), Document::description)

        }
        tw.setRowFactory(Callback<TableView<Document>, TableRow<Document>> {
            val tr = TableRow<Document>()
            tr.onMouseClicked = EventHandler {
                if (it.button == MouseButton.SECONDARY) {
                    AccountDeleteAction.execute()
             //       println(tw.items[tr.index])
                }
            }
            tr
        })

    }

}