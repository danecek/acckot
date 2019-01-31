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

class BalancePaneView() : Fragment() {

    val month: Property<Month> by params
    val syntaw =10
    val analw =10
    val amountw = 8.0


    val tw = tableview<BalanceItem>(Balance().createBalance(month.value)
            .observable()) {
        prefHeight = Options.prefTableHeight

        nestedColumn(Messages.Ucet.cm()) {
            column<BalanceItem, String>(Messages.Synteticky_ucet.cm()) {
                SimpleStringProperty(
                        if (it.value.group is AnalAcc) ""
                        else it.value.group?.toString()
                )
            }.weightedWidth(syntaw)
            column<BalanceItem, String>(Messages.Analytika.cm()) {
                SimpleStringProperty(
                        if (it.value.group is AnalAcc) (it.value.group as? AnalAcc)?.number
                        else ""
                )
            }.weightedWidth(analw)
            readonlyColumn(Messages.Jmeno.cm(), BalanceItem::name).remainingWidth()
        }
        nestedColumn(Messages.Pocatecni.cm()) {
            readonlyColumn(Messages.Aktiva.cm(), BalanceItem::initAssets).weightedWidth(amountw)
            column<BalanceItem, Long>(Messages.Pasiva.cm()) { t ->
                SimpleObjectProperty(-t.value.initLiabilities)
            }.weightedWidth(amountw)
        }
        nestedColumn(Messages.Obrat.cm()) {
            readonlyColumn(Messages.Ma_dati.cm(), BalanceItem::periodAssets).weightedWidth(amountw)
            column<BalanceItem, Long>(Messages.Dal.cm()) { t ->
                SimpleObjectProperty(-t.value.periodLiabilities)
            }.weightedWidth(amountw)
        }
        nestedColumn(Messages.Obrat_nacitany.cm()) {
            readonlyColumn(Messages.Ma_dati.cm(), BalanceItem::sumAssets).weightedWidth(amountw)
            column<BalanceItem, Long>(Messages.Dal.cm()) { t ->
                SimpleObjectProperty(-t.value.sumLiabilities)
            }.weightedWidth(amountw)

        }
        nestedColumn(Messages.Konecna.cm()) {
            readonlyColumn(Messages.Aktiva.cm(), BalanceItem::finalAssets).weightedWidth(amountw)
            column<BalanceItem, Long>(Messages.Pasiva.cm()) { t ->
                SimpleObjectProperty(-t.value.finalLiabilities)
            }.weightedWidth(amountw)
        }
        smartResize()
    }

    override val root: Parent = BalancePane(month.value, tw)
}