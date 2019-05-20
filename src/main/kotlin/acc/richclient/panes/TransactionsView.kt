package acc.richclient.panes

import acc.Options
import acc.business.Facade
import acc.model.Transaction
import acc.model.TransactionFilter
import acc.richclient.controller.AbstrAction
import acc.richclient.controller.ShowAllTransactionsAction
import acc.richclient.controller.ShowTransactionsAction
import acc.richclient.dialogs.TransactionDeleteDialog
import acc.richclient.dialogs.TransactionUpdateDialog
import acc.util.Messages
import acc.util.dayMonthFrm
import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import tornadofx.*

class TransactionsView : View(Messages.Transakce.cm()) {

    override val closeable: BooleanExpression
        get() = SimpleBooleanProperty(false)

    var tf = params[TransactionFilter::class.simpleName] as? TransactionFilter

    val idw = 5
    private val amw = 10
    val datew = 10
    val numberw = 10
    val namew = 10

    val tw = tableview(Facade.transactionsByFilter(tf).observable()) {
        prefHeight = Options.prefTableHeight
        readonlyColumn(Messages.Id.cm(), Transaction::id).weightedWidth(idw)
        column<Transaction, String>(Messages.Datum.cm()) { t ->
            SimpleStringProperty(dayMonthFrm.format(t.value.doc.date))
        }.weightedWidth(datew)

        readonlyColumn(Messages.Castka.cm(), Transaction::amount)
                .weightedWidth(amw)
        column<Transaction, String>(Messages.Ma_dati.cm()) { t ->
            SimpleStringProperty(t.value.maDati.number)
        }.weightedWidth(numberw)
                .cellDecorator {
                    tooltip {
                        text = items[index].maDati.name
                    }
                }

        column<Transaction, String>(Messages.Dal.cm()) { t ->
            SimpleStringProperty(t.value.dal.number)
        }.weightedWidth(numberw)
                .cellDecorator {
                    tooltip {
                        text = items[index].dal.name
                    }
                }

        column<Transaction, String>(Messages.Doklad.cm()) { t ->
            SimpleStringProperty(t.value.doc.id.toString())
        }.weightedWidth(namew)
                .cellDecorator {
                    tooltip {
                        text = items[index].doc.description
                    }
                }

        column<Transaction, String>(Messages.Zaplacena_faktura.cm()) { t ->
            SimpleStringProperty(t.value.relatedDoc?.id?.toString() ?: "")
        }.weightedWidth(namew)
                .cellDecorator {
                    tooltip {
                        text = items[index].relatedDoc?.description ?: ""
                    }
                }


        contextmenu {
            item(Messages.Zmen_transakci.cm()).action {
                tornadofx.find<TransactionUpdateDialog>(
                        params = mapOf("tr" to selectedItem)).openModal()
                //   PaneTabs.refreshTransactionPanes()
            }
            item(Messages.Zrus_transakci.cm()).action {
                tornadofx.find<TransactionDeleteDialog>(
                        params = mapOf("tr" to selectedItem)).openModal()
                //   PaneTabs.refreshTransactionPanes()
            }
        }
        smartResize()
    }

    override val root =  borderpane(){
        top= hbox(5) {
            label(tf?.toString()?: Messages.Vsechny.cm())
            button("first") {  }
        }
        center=tw
    }

    init {
        root.contextmenu {
            add(ShowAllTransactionsAction)
            add(ShowTransactionsAction)
        }
    }

    fun refresh(tf: TransactionFilter?) {
        // tp.text = tf.toString()
        tw.items.setAll(Facade.transactionsByFilter(tf))
    }
}


fun ContextMenu.add(a: AbstrAction) {
    val mi = MenuItem(a.name)
    mi.onAction = EventHandler(a)
    items.add(mi)
}