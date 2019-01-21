package acc.richclient.views.panes

import acc.business.Facade
import acc.business.balance.Balance
import acc.business.balance.BalanceItem
import acc.model.AnalAcc
import acc.util.Messages
import acc.util.monthFormater
import acc.util.withColon
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*
import java.time.Month

class BalancePane(private val month: Month, private val content : TableView<BalanceItem>)  :
        TitledPane(Messages.Rozvaha_pro_mesic.cm().withColon + monthFormater.format(month), content) {

    fun refresh() {
        content.items.setAll(Balance().createBalance(month))
    }

    val selected:BalanceItem?
        get() =  content.selectedItem
}