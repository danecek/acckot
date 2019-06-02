package acc.richclient.panes

import acc.model.UnpaidInvoicesFilter
import javafx.scene.Node
import javafx.scene.control.Tab
import tornadofx.*

class PaneTabs : View() {

    override val root = tabpane() {
        tab<AccountsView>()
        tab<DocumentsView>()
        tab<TransactionsView>()
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

        inline fun <reified T : View> selectView() {
            val tv = find<T>()
            find<PaneTabs>().root.tabs.find {
                it.content == tv.root
            }?.select()
        }

        fun clearIncomeAndBalance() {
            find<PaneTabs>().root.tabs.removeIf {
                it.content is BalancePane || it.content is IncomePane
            }
        }
    }

}

