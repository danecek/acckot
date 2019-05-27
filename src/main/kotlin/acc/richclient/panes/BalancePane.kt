package acc.richclient.panes

import acc.business.balance.StatItem
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane

class BalancePane(private val title: String, private val content: TableView<StatItem>) :
        TitledPane(title, content) {
    }