package acc.richclient.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.Document
import acc.util.Messages
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*

class AccountPane(val content: TableView<AnalAcc>) : TitledPane(Messages.Ucty.cm(), content) {

    fun refresh() {
        content.items.setAll(Facade.allAccounts.observable())
    }

    val selected: AnalAcc?
        get() =  content.selectedItem
}