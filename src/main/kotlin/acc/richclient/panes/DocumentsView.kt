package acc.richclient.panes

import acc.Options
import acc.business.Facade
import acc.model.DocFilter
import acc.model.Document
import acc.model.TransactionFilter
import acc.richclient.dialogs.docs.DocumentDeleteDialog
import acc.richclient.dialogs.docs.DocumentUpdateDialog
import acc.richclient.dialogs.trans.TransactionCreateDialog
import acc.util.Messages
import acc.util.dayMonthFrm
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import tornadofx.*

open class DocumentsView : View(Messages.Doklady.cm()) {

    override val closeable = SimpleBooleanProperty(false)

    private val tableView = tableview(mutableListOf<Document>().observable()) {
        prefHeight = Options.prefTableHeight
        column<Document, String>(Messages.Typ.cm()) {
            SimpleStringProperty(it.value.type.abbr)
        }
        readonlyColumn(Messages.Cislo.cm(), Document::number)
        column<Document, String>(Messages.Datum.cm()) {
            SimpleStringProperty(dayMonthFrm.format(it.value.date))
        }
        readonlyColumn(Messages.Popis.cm(), Document::description)
        contextmenu {
            item(Messages.Zmen_doklad.cm()) {
                action { find<DocumentUpdateDialog>(params = mapOf("doc" to selectedItem)).openModal() }
            }
            item(Messages.Zrus_doklad.cm()) {
                onShowing = EventHandler {
                    isDisable = !Facade.transactionsByFilter(TransactionFilter(doc = selectedItem))
                            .isEmpty()
                }
                action {
                    find<DocumentDeleteDialog>(params = mapOf("doc" to selectedItem)).openModal()
                }
            }
            item(Messages.Zauctuj_doklad.cm()) {
                action {
                    find<TransactionCreateDialog>(params = mapOf("doc" to selectedItem)).openModal()
                }
            }
/*            item(Messages.Zauctovane_transakce.cm()) {
                action {
                    PaneTabs.addTab(Messages.Transakce.cm(),
                            find<TransactionsView>(
                                    mapOf("transFilter" to TransactionFilter(doc = selectedItem))).root)

                }
            }*/
        }
        smartResize()
    }

    override val root = titledpane {
        isCollapsible = false
        content = tableView
    }

    var docFilter: DocFilter? = null
        set(value) {
            field = value
            root.text = value?.toString() ?: Messages.Vsechny_doklady.cm()
            update()
        }

    init {
       docFilter = null // vyvolani setru
    }

    fun update() {
        tornadofx.runAsync {
            Facade.documentsByFilter(docFilter)
        } fail {
            error(it)
        } ui {
            tableView.items.setAll(it)
        }
    }
}
