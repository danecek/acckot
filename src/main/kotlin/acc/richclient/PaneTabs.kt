package acc.richclient

import acc.model.UnpaidInvoicesFilter
import acc.richclient.panes.*
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

        fun clearIncomeBalance() {
            find<PaneTabs>().root.tabs.removeIf {
                it.content is BalancePane || it.content is IncomePane
            }
        }
    }

}


