package acc.richclient.views.panes

import acc.business.Facade
import acc.model.Transaction
import acc.model.TransactionFilter
import acc.util.Messages
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*

class TransactionPane(val content: TableView<Transaction>, val tf : TransactionFilter)
    : TitledPane(Messages.Transakce.cm()+ tf, content) {

    fun refresh() {
        content.items.setAll(Facade.getTransactions(tf).observable())
    }

}