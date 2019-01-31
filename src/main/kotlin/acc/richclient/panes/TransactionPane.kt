package acc.richclient.panes

import acc.business.Facade
import acc.model.Transaction
import acc.model.TransactionFilter
import acc.util.Messages
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*

class TransactionPane(val content: TableView<Transaction>, val tf : TransactionFilter?)
    : TitledPane(tf?.toString()?: Messages.Vsechny.cm(), content) {

    fun refresh() {
        content.items.setAll(Facade.transactionsByFilter(tf).observable())
    }

}