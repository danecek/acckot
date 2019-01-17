package acc.richclient.views

import acc.model.AnalAcc
import acc.model.Document
import acc.model.Transaction
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

/*
    fun refreshTransactionPanes() {
        println("refreshTransactionPanes")
*/
/*        findAll<TransactionsFragment>().stream().forEach {
            println(it)
            it.refresh()
        }*//*

        find<TransactionsFragment>().refresh()
    }
*/

    companion object {

        private fun tabs(): ObservableList<Tab> {
            return find<PaneTabs>().root.tabs
        }

        fun addTab(text: String, node: Node) {
            tabs().add(Tab(text, node))
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

        val selectedTransaction: Transaction?
            get() = transactionPanes
                    .filter { it.selected != null }
                    .findFirst().orElse(null)?.selected

        // documents
        val documentPane: DocumentPane?
            get() = tabs().stream()
                    .filter { t -> t.text == Messages.Doklad.cm() }
                    .map { t: Tab -> (t.content as DocumentPane) }
                    .findFirst().orElse(null)

        fun refreshDocumentPane() {
            documentPane?.refresh()
        }

        val selectedDocument: Document?
            get() = documentPane?.selected

        // accounts
        val accountPane: AccountPane?
            get() = tabs().stream()
                    .filter { t -> t.text == Messages.Ucty.cm() }
                    .map { t: Tab -> (t.content as AccountPane) }
                    .findFirst().orElse(null)
        
        fun refreshAccountPane() {
            accountPane?.refresh()
        }



        val selectedAccount: AnalAcc?
            get() = accountPane?.selected


    }

}