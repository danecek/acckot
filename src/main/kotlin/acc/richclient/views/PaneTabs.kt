package acc.richclient.views

import acc.richclient.panes.TransactionsPane
import acc.util.Messages
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.control.Tab
import tornadofx.*
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

        val transactionPanes: Stream<TransactionsPane>
            get() {
                return tabs().stream()
                        .filter { t -> t.text == Messages.Transakce.cm() }
                        .map { t -> t.content as TransactionsPane }
            }

        fun refreshTransactionPanes() {
            transactionPanes.forEach { it.refresh() }
        }

 /*       val accountPane: AccountPane?
            get() {
                val t = tabs().stream()
                        .filter { t -> t.text == Messages.Ucty.cm() }
                        .findFirst()
                return if (t.isPresent) t.get().content as AccountPane else null
            }

        fun refreshAccountPane() {
            accountPane?.refresh()
        }*/


    }

}