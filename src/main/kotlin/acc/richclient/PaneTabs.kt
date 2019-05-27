package acc.richclient

import acc.model.UnpaidInvoicesFilter
import acc.richclient.panes.AccountsView
import acc.richclient.panes.DocumentsView
import acc.richclient.panes.TransactionsView
import javafx.scene.Node
import javafx.scene.control.Tab
import tornadofx.*

class PaneTabs : View() {

    override val root = tabpane() {
        tab<TransactionsView>()
        tab<AccountsView>()
        tab<DocumentsView>()
    }

    companion object {

/*        private fun tabs(): ObservableList<Tab> {
            return find<PaneTabs>().root.tabs
        }*/

        fun addTab(text: String, node: Node) {
            val t = Tab(text, node)
            find<PaneTabs>().root.tabs.add(t)
            t.select()
        }


        fun showUnpaidInvoicesPane() {
            with(find<DocumentsView>()) {
                docFilter = UnpaidInvoicesFilter
                update()
            }

        }
    }

}


