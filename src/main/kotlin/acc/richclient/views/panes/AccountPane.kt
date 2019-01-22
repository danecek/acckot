package acc.richclient.views.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.util.Messages
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import javafx.scene.layout.StackPane
import tornadofx.*

class AccountPane(val content: TableView<AnalAcc>) : StackPane(content) {

    fun refresh() {
        content.items.setAll(Facade.allAccounts.observable())
    }

}