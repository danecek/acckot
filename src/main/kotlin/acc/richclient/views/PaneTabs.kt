package acc.richclient.views

import acc.richclient.panes.TransactionsPane
import acc.util.Messages
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.control.Tab
import tornadofx.View
import tornadofx.find
import tornadofx.tabpane
import java.util.stream.Stream

class PaneTabs : View() {

    override val root = tabpane()


    companion object {

        private fun tabs(): ObservableList<Tab> {
            return find<PaneTabs>().root.tabs
        }

        fun addTab(text: String, node: Node) {
            tabs().add(Tab(text, node))
        }

        fun getTransactionPanes(): Stream<TransactionsPane> {
            return tabs().stream()
                    .filter { t -> t.getText() == Messages.Transakce.cm() }
                    .map { t -> t.getContent() as TransactionsPane }
        }

        fun refreshTransactionPanes() {
            getTransactionPanes().forEach { it.refresh() }
        }

    }

}