package acc.richclient.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.richclient.controller.actions.AccountDeleteAction
import acc.util.Messages
import javafx.event.EventHandler
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.input.MouseButton
import javafx.util.Callback
import tornadofx.*

class AccountPane : View() {

    val selected: AnalAcc?
        get() = (root.content as TableView<AnalAcc>).selectedItem

    override val root = titledpane {

        title = Messages.Ucty.cm()

        val tw = tableview<AnalAcc>(Facade.allAccounts.observable()) {
            readonlyColumn(Messages.Synteticky_ucet.cm(), AnalAcc::syntAccount)
            readonlyColumn(Messages.Analytika.cm(), AnalAcc::anal)
            readonlyColumn(Messages.Jmeno.cm(), AnalAcc::fullName)

        }
        tw.setRowFactory(Callback<TableView<AnalAcc>, TableRow<AnalAcc>> {
            val tr = TableRow<AnalAcc>()
            tr.onMouseClicked = EventHandler {
                if (it.button == MouseButton.SECONDARY) {
                    AccountDeleteAction.execute()
                    println(tw.items[tr.index])
                }
            }
            tr
        })

    }

}