package acc.richclient.views.panes

import acc.business.balance.Balance
import acc.business.balance.BalanceItem
import acc.util.Messages
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Parent
import tornadofx.*
import java.time.Month

class BalanceView() : Fragment() {

    val month: Property<Month> by params

    val tw = tableview<BalanceItem>(Balance().createBalance(month.value)
            .observable()) {

        prefHeight = Double.MAX_VALUE

        //    columnResizePolicy = TableView.UNCONSTRAINED_RESIZE_POLICY
        nestedColumn(Messages.Ucet.cm()) {
            readonlyColumn(Messages.Synteticky_ucet.cm(), BalanceItem::group)
            readonlyColumn(Messages.Analytika.cm(), BalanceItem::number)
            readonlyColumn(Messages.Jmeno.cm(), BalanceItem::name)
        }
        nestedColumn(Messages.Pocatecni.cm()) {
            readonlyColumn(Messages.Aktiva.cm(), BalanceItem::initAssets)
            column<BalanceItem, Long>(Messages.Pasiva.cm()) { t ->
                SimpleObjectProperty(-t.value.initLiabilities)
            }
        }
        nestedColumn(Messages.Obrat.cm()) {
            readonlyColumn(Messages.Ma_dati.cm(), BalanceItem::assets)
            column<BalanceItem, Long>(Messages.Dal.cm()) { t ->
                SimpleObjectProperty(-t.value.liabilities)
            }
        }
        nestedColumn(Messages.Obrat_nacitany.cm()) {
            readonlyColumn(Messages.Ma_dati.cm(), BalanceItem::assetsSum)
            column<BalanceItem, Long>(Messages.Dal.cm()) { t ->
                SimpleObjectProperty(-t.value.liabilitiesSum)
            }

        }
        nestedColumn(Messages.Konecna.cm()) {
            readonlyColumn(Messages.Aktiva.cm(), BalanceItem::finalAssets)
            column<BalanceItem, Long>(Messages.Pasiva.cm()) { t ->
                SimpleObjectProperty(-t.value.finalLiabilities)
            }
        }
    }

    override val root: Parent = BalancePane(month.value, tw)
}