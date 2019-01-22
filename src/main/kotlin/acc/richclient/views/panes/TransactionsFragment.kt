package acc.richclient.views.panes

import acc.business.Facade
import acc.model.Transaction
import acc.model.TransactionFilter
import acc.richclient.views.PaneTabs
import acc.richclient.views.dialogs.TransactionDeleteDialog
import acc.richclient.views.dialogs.TransactionUpdateDialog
import acc.util.Global
import acc.util.Messages
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate
import java.util.*

class TransactionsFragment : Fragment() {

    val tf: TransactionFilter by params

    val tw = tableview(Facade.allTransactions.observable()) {
        readonlyColumn(Messages.Id.cm(), Transaction::id)

        readonlyColumn(Messages.Castka.cm(), Transaction::amount)
        nestedColumn(Messages.Ma_dati.cm()) {
            column<Transaction, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.maDati.number)
            }
            column<Transaction, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.maDati.fullName)
            }
        }
        nestedColumn(Messages.Dal.cm()) {
            column<Transaction, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.dal.number)
            }
            column<Transaction, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.dal.fullName)
            }
        }
        nestedColumn(Messages.Doklad.cm()) {
            column<Transaction, String>(Messages.Datum.cm()) { t ->
                SimpleStringProperty(Global.df.format(t.value.document.date))
            }
            column<Transaction, String>(Messages.Typ_dokladu.cm()) { t ->
                SimpleStringProperty(t.value.document.type.text)
            }
            column<Transaction, Int>(Messages.Cislo.cm()) { t ->
                SimpleObjectProperty<Int>(t.value.document.number)
            }
            column<Transaction, String>(Messages.Popis.cm()) { t ->
                SimpleStringProperty(t.value.document.description)
            }

        }
        fun LocalDate.x() = Global.df.format(this)
        nestedColumn(Messages.Souvisejici_faktura.cm()) {
            column<Transaction, String>(Messages.Datum.cm()) { t ->
                SimpleStringProperty(t.value.relatedDocument?.date?.x()?:"")
            }
            column<Transaction, String>(Messages.Typ_dokladu.cm()) { t ->
                SimpleStringProperty(t.value.relatedDocument?.type?.text?:"")
            }
            column<Transaction, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.relatedDocument?.number?.toString()?:"")
            }
            column<Transaction, String>(Messages.Popis.cm()) { t ->
                SimpleStringProperty(t.value.relatedDocument?.description?:"")
            }

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

    }
    override val root = TransactionPane(tw, tf)

}