package acc.richclient

import acc.richclient.panes.AccountPane
import acc.richclient.panes.DocumentPane
import acc.richclient.panes.TransactionPane
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
            val t = Tab(text, node)
            tabs().add(t)
            t.select()
        }

        // transactions
        val transactionPanes: Stream<TransactionPane>
            get() = tabs().stream()
                    .filter { t -> t.text == Messages.Transakce.cm() }
                    .map { t: Tab -> (t.content as TransactionPane) }

        fun refreshTransactionPanes() {
            transactionPanes
                    .forEach {
                        it.refresh()
                    }
        }

        // documents
        val documentPane: DocumentPane?
            get() = tabs().stream()
                    .filter { t -> t.text == Messages.Doklady.cm() }
                    .map { t: Tab -> (t.content as DocumentPane) }
                    .findFirst().orElse(null)

        fun refreshDocumentPane() {
            documentPane?.refresh()
        }

        // accountsFile
        val accountPane: AccountPane?
            get() = tabs().stream()
                    .filter { t -> t.text == Messages.Ucty.cm() }
                    .map { t: Tab -> (t.content as AccountPane) }
                    .findFirst().orElse(null)

        fun refreshAccountPane() {
            accountPane?.refresh()
        }

        fun refreshDocAndTransPane() {
            refreshDocumentPane()
            refreshTransactionPanes()
          }

    }

}