package acc.richclient.panes

import acc.Options
import acc.business.balance.StatItem
import acc.business.balance.Statement
import acc.model.AnalAcc
import acc.util.Messages
import acc.util.monthFrm
import acc.util.withColon
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import tornadofx.*
import java.time.Month

open class SheetPaneFragment() : Fragment() {

    private val month: Property<Month> by params
    private val includeSyntAccount: Boolean by params
    private val sheet: Messages by params
    private val syntaw = 10
    private val analw = 10
    private val amountw = 8.0
    private val orderw = 2.0

    val tableView = tableview<StatItem>(Statement()
            .createItemList(sheet, month.value, includeSyntAccount)
            .asObservable()) {
        prefHeight = Options.prefTableHeight

        readonlyColumn(Messages.Poradi.cm(), StatItem::order).weightedWidth(orderw)

        nestedColumn(Messages.Ucet.cm()) {
            column<StatItem, String>(Messages.Syntetika.cm()) {
                SimpleStringProperty(
                        if (it.value.acc is AnalAcc) ""
                        else it.value.name//group?.toString()
                )
            }.weightedWidth(syntaw)
            column<StatItem, String>(Messages.Analytika.cm()) {
                SimpleStringProperty(
                        if (it.value.acc is AnalAcc)
                            it.value.acc?.number
                        else ""
                )
            }.weightedWidth(analw)
            column<StatItem, String>(Messages.Jmeno.cm()) {
                SimpleStringProperty(
                        if (it.value.acc is AnalAcc)
                            it.value.acc?.name
                        else ""
                )
            }.weightedWidth(analw)

        }
        if (sheet == Messages.Rozvaha)
            nestedColumn(Messages.Pocatecni.cm()) {
                readonlyColumn(Messages.Aktiva.cm(), StatItem::initAssets).weightedWidth(amountw)
                column<StatItem, Long>(Messages.Pasiva.cm()) { t ->
                    SimpleObjectProperty(t.value.initLiabilities)
                }.weightedWidth(amountw)
            }
        nestedColumn(Messages.Obrat.cm()) {
            readonlyColumn(Messages.Ma_dati.cm(), StatItem::periodDebits).weightedWidth(amountw)
            column<StatItem, Long>(Messages.Dal.cm()) { t ->
                SimpleObjectProperty(t.value.periodCredits)
            }.weightedWidth(amountw)
        }
        nestedColumn(Messages.Obrat_nacitany.cm()) {
            readonlyColumn(Messages.Ma_dati.cm(), StatItem::sumDebits).weightedWidth(amountw)
            column<StatItem, Long>(Messages.Dal.cm()) { t ->
                SimpleObjectProperty(t.value.sumCredits)
            }.weightedWidth(amountw)

        }
        if (sheet == Messages.Rozvaha)
            nestedColumn(Messages.Konecna.cm()) {
                readonlyColumn(Messages.Aktiva.cm(), StatItem::finalAssets).weightedWidth(amountw)
                column<StatItem, Long>(Messages.Pasiva.cm()) { t ->
                    SimpleObjectProperty(t.value.finalLiabilities)
                }.weightedWidth(amountw)
            }
        smartResize()
    }

    val ttl = when (sheet) {
        Messages.Rozvaha -> Messages.Rozvaha_pro_mesic
        Messages.Zisky_a_ztraty -> Messages.Zisky_a_ztraty_za_mesic
        else -> kotlin.error(sheet.toString())
    }.cm().withColon + monthFrm.format(month.value)


    override val root: Parent = BalancePane(ttl, tableView)
}