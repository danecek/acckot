package acc.richclient.panes

import acc.business.Facade
import acc.model.Transaction
import acc.model.TransactionFilter
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*

class TransactionPane(val content: TableView<Transaction>, val tf : TransactionFilter)
    : TitledPane(tf.toString(), content) {

    fun refresh() {
        content.items.setAll(Facade.transactionsByFilter(tf).observable())
    }

}