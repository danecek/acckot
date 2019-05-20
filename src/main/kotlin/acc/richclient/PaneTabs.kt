package acc.richclient

import acc.model.DocFilter
import acc.model.TransactionFilter
import acc.model.UnpaidInvoicesFilter
import acc.richclient.panes.*
import acc.util.Messages
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.control.Tab
import tornadofx.*
import java.util.stream.Stream

class PaneTabs : View() {

    override val root = tabpane() {
        tab<TransactionsView> {
            this.content.contextmenu {
                item("xxx") {

                }
            }

        }

    }

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
/*        private val transactionPanes: Stream<TransactionPane>
            get() = tabs().stream()
                    .filter { it.content is TransactionPane }
                    .map { it.content as TransactionPane }*/

        fun showTransactionPane(tf: TransactionFilter? = null) {
            //    PaneTabs.addTab(Messages.Transakce.cm(),
            val tpf = find<TransactionsView>()
            //     params =               mapOf(TransactionFilter::class.simpleName to tf)).root
          //  tpf.tf = tf
            tpf.refresh(tf)
        }

/*        fun refreshTransactionPanes() {
            transactionPanes
                    .forEach {
                        it.refresh()
                    }
        }*/

        // documents
        val documentPanes: Stream<DocumentPane>
            get() = tabs().stream()
                    .filter { it.content is DocumentPane }
                    .map { it.content as DocumentPane }


        fun showDocumentPane(df: DocFilter? = null) {
            PaneTabs.addTab(Messages.Doklady.cm(),
                    find<DocumentPaneFragment>(params = mapOf(DocFilter::class.simpleName to df)).root)
        }

        fun showUnpaidInvoicesPane() {
            PaneTabs.addTab(Messages.Doklady.cm(),
                    find<DocumentPaneFragment>(params =
                    mapOf(DocFilter::class.simpleName to UnpaidInvoicesFilter)).root)
        }


        fun refreshDocumentPanes() {
            documentPanes.forEach {
                it.refresh()
            }
        }

        fun refreshDocAndTransPane() {
            refreshDocumentPanes()
         //   refreshTransactionPanes()
        }

        // accounts
        val accountPane: AccountPane?
            get() = tabs().stream()
                    .filter { it.content is AccountPane }
                    .map { it.content as AccountPane }
                    .findFirst().orElse(null)

        fun refreshAccountPane() {
            accountPane?.refresh()
        }

        fun showAccountPane() {
            if (accountPane == null)
                PaneTabs.addTab(Messages.Ucty.cm(), find<AccountPaneView>().root)
        }


    }

}
