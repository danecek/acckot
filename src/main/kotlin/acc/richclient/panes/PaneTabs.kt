package acc.richclient.panes

import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.control.Tab
import tornadofx.*

class PaneTabs : View() {

    lateinit var accsTab: Tab
    lateinit var docsTab: Tab
    lateinit var transTab: Tab

    override val root = tabpane() {
        accsTab = tab<AccountsView>()
        docsTab = tab<DocumentsView>()
        transTab = tab<TransactionsView>()
    }

    companion object {

        val self
                get() = find<PaneTabs>()

        fun addTab(text: String, node: Node) {
            val t = Tab(text, node)
            find<PaneTabs>().root.tabs.add(t)
            t.select()
        }

        fun syncSelf(block: PaneTabs.() -> Unit) {
            Platform.runLater {
                block(find<PaneTabs>())
            }
        }

        fun clearIncomeAndBalance() {
            syncSelf {
                root.tabs.removeIf {
                    it.content is BalancePane || it.content is IncomePane
                }
            }
        }
    }

}