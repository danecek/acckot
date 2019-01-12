package acc.views

import javafx.scene.Node
import javafx.scene.control.Tab
import tornadofx.View
import tornadofx.find
import tornadofx.tabpane

class PaneTabs : View() {

    override val root = tabpane()

    companion object {
        fun addTab(text: String, node: Node) {
            find<PaneTabs>().root.tabs.add(Tab(text, node))
        }

    }

}