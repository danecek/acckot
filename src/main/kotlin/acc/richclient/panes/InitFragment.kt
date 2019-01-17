package acc.richclient.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.Init
import acc.richclient.controller.actions.TransactionDeleteAction
import acc.util.Messages
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.input.MouseButton
import javafx.util.Callback
import tornadofx.*

class InitFragment : Fragment() {

    val acc = params["acc"] as? AnalAcc

    val tw = tableview(Facade.getInits(acc).observable()) {
        readonlyColumn(Messages.Id.cm(), Init::id)

        readonlyColumn(Messages.Castka.cm(), Init::amount)
        nestedColumn(Messages.Ma_dati.cm()) {
            column<Init, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.maDati.number.toString())
            }
            column<Init, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.maDati.fullName)
            }
        }
        nestedColumn(Messages.Dal.cm()) {
            column<Init, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.dal.number)
            }
            column<Init, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.dal.fullName)
            }
        }

        setRowFactory(Callback<TableView<Init>, TableRow<Init>>
        {
            val tr = TableRow<Init>()
            tr.onMouseClicked = EventHandler {
                if (it.button == MouseButton.SECONDARY) {
                    TransactionDeleteAction.execute()
                }
            }
            tr
        })
    }
    override val root = InitPane(tw, acc)

}