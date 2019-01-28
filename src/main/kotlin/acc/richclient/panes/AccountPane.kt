package acc.richclient.panes

import acc.business.Facade
import acc.model.AnalAcc
import javafx.scene.control.TableView
import javafx.scene.layout.StackPane

class AccountPane(val content: TableView<AnalAcc>) : StackPane(content) {

    fun refresh() {
        content.items.setAll(Facade.allAccounts)
    }

}