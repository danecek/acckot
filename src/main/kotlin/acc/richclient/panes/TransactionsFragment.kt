package acc.richclient.panes

import acc.Options
import acc.business.Facade
import acc.model.Transaction
import acc.model.TransactionFilter
import acc.richclient.PaneTabs
import acc.richclient.dialogs.TransactionDeleteDialog
import acc.richclient.dialogs.TransactionUpdateDialog
import acc.util.Messages
import acc.util.dayMonthFrm
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableColumn
import tornadofx.*

class TransactionsFragment : Fragment() {

    val tf: TransactionFilter by params


    val idw = 5
    val amw = 10
    val dscw = 20
    val datew = 10
    val numberw = 10
    val namew = 20
    val totalw = (idw + amw + 2 * (numberw + namew) + datew + namew + dscw + namew) / 100.0
    fun <T> TableColumn<Transaction, T>.mw(w: Int) {
        this.pctWidth(w / totalw)
    }

    val tw = tableview(Facade.transactionsByFilter(tf).observable()) {
        prefHeight = Options.prefTableHeight
        readonlyColumn(Messages.Id.cm(), Transaction::id).mw(idw)
        column<Transaction, String>(Messages.Datum.cm()) { t ->
            SimpleStringProperty(dayMonthFrm.format(t.value.doc.date))
        }.mw(datew)

        readonlyColumn(Messages.Castka.cm(), Transaction::amount)
                .mw(amw)
        nestedColumn(Messages.Ma_dati.cm()) {
            column<Transaction, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.maDati.number)
            }.mw(numberw)
            column<Transaction, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.maDati.name)
            }.mw(namew)
        }
        nestedColumn(Messages.Dal.cm()) {
            column<Transaction, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.dal.number)
            }.mw(numberw)
            column<Transaction, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.dal.name)
            }.mw(namew)
        }
        nestedColumn(Messages.Doklad.cm()) {

            column<Transaction, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.doc.id.toString())
            }.mw(namew)
            column<Transaction, String>(Messages.Popis.cm()) { t ->
                SimpleStringProperty(t.value.doc.description)
            }.mw(dscw)

        }
        nestedColumn(Messages.Souvisejici_doklad.cm()) {
            column<Transaction, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.relatedDocId?.id?.toString() ?: "")
            }.mw(namew)

        }
        contextmenu {
            item(Messages.Zmen_transakci.cm()).action {
                tornadofx.find<TransactionUpdateDialog>(
                        params = mapOf("tr" to selectedItem)).openModal()
                PaneTabs.refreshTransactionPanes()
            }
            item(Messages.Zrus_transakci.cm()).action {
                tornadofx.find<TransactionDeleteDialog>(
                        params = mapOf("tr" to selectedItem)).openModal()
                PaneTabs.refreshTransactionPanes()
            }
        }
        smartResize()

    }
    override val root = TransactionPane(tw, tf)

}