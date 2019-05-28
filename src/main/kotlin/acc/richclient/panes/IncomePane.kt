package acc.richclient.panes

import acc.business.balance.StatItem
import acc.util.Messages
import acc.util.monthFrm
import acc.util.withColon
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import java.time.Month

class IncomePane(month: Month, content: TableView<StatItem>) :
        TitledPane(Messages.Rozvaha_pro_mesic.cm().withColon + monthFrm.format(month), content) {
}