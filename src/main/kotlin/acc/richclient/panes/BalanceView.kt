package acc.richclient.panes

import acc.Options
import acc.business.balance.Balance
import acc.business.balance.BalanceItem
import acc.model.AnalAcc
import acc.util.Messages
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import tornadofx.*
import java.time.Month

class BalanceView() : Fragment() {

    val month: Property<Month> by params
    val p = 8.0

    val tw = tableview<BalanceItem>(Balance().createBalance(month.value)
            .observable()) {
        prefHeight = Options.prefTableHeight

        nestedColumn(Messages.Ucet.cm()) {
            column<BalanceItem, String>(Messages.Synteticky_ucet.cm()) {
                SimpleStringProperty(
                        if (it.value.group is AnalAcc) ""
                        else it.value.group?.toString()
                )
            }.pctWidth(10)
            column<BalanceItem, String>(Messages.Analytika.cm()) {
                SimpleStringProperty(
                        if (it.value.group is AnalAcc) (it.value.group as? AnalAcc)?.number
                        else ""
                )
            }.pctWidth(10)
            readonlyColumn(Messages.Jmeno.cm(), BalanceItem::name).remainingWidth()
        }
        nestedColumn(Messages.Pocatecni.cm()) {
            readonlyColumn(Messages.Aktiva.cm(), BalanceItem::initAssets).pctWidth(p)
            column<BalanceItem, Long>(Messages.Pasiva.cm()) { t ->
                SimpleObjectProperty(-t.value.initLiabilities)
            }.pctWidth(p)
        }
        nestedColumn(Messages.Obrat.cm()) {
            readonlyColumn(Messages.Ma_dati.cm(), BalanceItem::periodAssets).pctWidth(p)
            column<BalanceItem, Long>(Messages.Dal.cm()) { t ->
                SimpleObjectProperty(-t.value.periodLiabilities)
            }.pctWidth(p)
        }
        nestedColumn(Messages.Obrat_nacitany.cm()) {
            readonlyColumn(Messages.Ma_dati.cm(), BalanceItem::sumAssets).pctWidth(p)
            column<BalanceItem, Long>(Messages.Dal.cm()) { t ->
                SimpleObjectProperty(-t.value.sumLiabilities)
            }.pctWidth(p)

        }
        nestedColumn(Messages.Konecna.cm()) {
            readonlyColumn(Messages.Aktiva.cm(), BalanceItem::finalAssets).pctWidth(p)
            column<BalanceItem, Long>(Messages.Pasiva.cm()) { t ->
                SimpleObjectProperty(-t.value.finalLiabilities)
            }.pctWidth(p)
        }
        smartResize()
    }

    override val root: Parent = BalancePane(month.value, tw)
}