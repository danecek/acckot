package acc.richclient.panes

import acc.business.Facade
import acc.model.Transaction
import acc.model.TransactionFilter
import acc.richclient.controller.actions.TransactionDeleteAction
import acc.util.Global
import acc.util.Messages
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.input.MouseButton
import javafx.util.Callback
import tornadofx.*

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
            column<Transaction, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.document.number)
            }
            column<Transaction, String>(Messages.Popis.cm()) { t ->
                SimpleStringProperty(t.value.document.description)
            }

        }
        setRowFactory(Callback<TableView<Transaction>, TableRow<Transaction>>
        {
            val tr = TableRow<Transaction>()
            tr.onMouseClicked = EventHandler {
                if (it.button == MouseButton.SECONDARY) {
                    TransactionDeleteAction.execute()
                }
            }
            tr
        })
    }
    override val root = TransactionPane(tw, tf)

}