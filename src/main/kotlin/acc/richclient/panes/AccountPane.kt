package acc.richclient.panes

import acc.business.Facade
import acc.model.AnalAcc
import javafx.scene.control.TableView
import javafx.scene.layout.StackPane
import tornadofx.*

class AccountPane(val content: TableView<AnalAcc>) : StackPane(content) {

    fun refresh() {
        runAsync {
            Facade.allAccounts
        } ui {
            content.items.setAll(it)
        }

    }

}