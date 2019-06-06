package acc.richclient.panes

import acc.Options
import acc.business.Facade
import acc.model.Transaction
import acc.model.TransactionFilter
import acc.richclient.dialogs.trans.TransactionDeleteDialog
import acc.richclient.dialogs.trans.TransactionUpdateDialog
import acc.util.Messages
import acc.util.accFail
import acc.util.dayMonthFrm
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class TransactionsView : View(Messages.Transakce.cm()) {

    override val closeable = SimpleBooleanProperty(false)

    val tw = tableview(mutableListOf<Transaction>().asObservable()) {
        prefHeight = Options.prefTableHeight
        readonlyColumn(Messages.Poradi.cm(), Transaction::id).weightedWidth(idWidth)
        column<Transaction, String>(Messages.Doklad.cm()) { t ->
            SimpleStringProperty(t.value.doc.name)
        }.weightedWidth(nameWidth)
                .cellDecorator {
                    tooltip {
                        text = items[index].doc.description
                    }
                }

        column<Transaction, String>(Messages.Datum.cm()) { t ->
            SimpleStringProperty(dayMonthFrm.format(t.value.doc.date))
        }.weightedWidth(dateWidth)

        readonlyColumn(Messages.Castka.cm(), Transaction::amount)
                .weightedWidth(amountWidth)

        column<Transaction, String>(Messages.Ma_dati.cm()) { t ->
            SimpleStringProperty(t.value.maDati.number)
        }.weightedWidth(numberWidth)
                .cellDecorator {
                    tooltip {
                        text = items[index].maDati.name
                    }
                }

        column<Transaction, String>(Messages.Dal.cm()) { t ->
            SimpleStringProperty(t.value.dal.number)
        }.weightedWidth(numberWidth)
                .cellDecorator {
                    tooltip {
                        text = items[index].dal.name
                    }
                }



        column<Transaction, String>(Messages.Zaplacena_faktura.cm()) { t ->
            SimpleStringProperty(t.value.relatedDoc?.name ?: "")
        }.weightedWidth(nameWidth)
                .cellDecorator {
                    tooltip {
                        text = items[index].relatedDoc?.description ?: ""
                    }
                }

        contextmenu {
            item(Messages.Zmen_transakci.cm()).action {
                tornadofx.find<TransactionUpdateDialog>(
                        params = mapOf("tr" to selectedItem)).openModal()
            }
            item(Messages.Zrus_transakci.cm()).action {
                tornadofx.find<TransactionDeleteDialog>(
                        params = mapOf("tr" to selectedItem)).openModal()
                //   PaneTabs.refreshTransactionPanes()
            }
        }
        smartResize()
    }

    override val root = titledpane {
        isCollapsible = false
        content = tw
    }


    var transFilter: TransactionFilter? = null
        set(value) {
            field = value
            root.text = value?.toString() ?: Messages.Vsechny_transakce.cm()
            update()
        }

    init {
        transFilter = null  // vyvolani setru
    }

    fun update() {
        tornadofx.runAsync {
            Facade.transactionsByFilter(transFilter)
        } fail {
            accFail(it)
        } ui {
            tw.items.setAll(it)
            PaneTabs.selectView<TransactionsView>()
        }
    }

}


