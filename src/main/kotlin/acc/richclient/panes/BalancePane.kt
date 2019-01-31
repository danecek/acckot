package acc.richclient.panes

import acc.business.balance.Balance
import acc.business.balance.BalanceItem
import acc.util.Messages
import acc.util.monthFrm
import acc.util.withColon
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import java.time.Month

class BalancePane(private val month: Month, private val content: TableView<BalanceItem>) :
        TitledPane(Messages.Rozvaha_pro_mesic.cm().withColon + monthFrm.format(month), content) {

    fun refresh() {
        content.items.setAll(Balance().createBalance(month))
    }

}