package acc.richclient.panes

import acc.Options
import acc.business.Facade
import acc.model.DocFilter
import acc.model.Document
import acc.model.TransactionFilter
import acc.util.dayMonthFrm
import acc.richclient.dialogs.DocumentDeleteDialog
import acc.richclient.dialogs.DocumentUpdateDialog
import acc.richclient.dialogs.TransactionCreateDialog
import acc.util.Messages
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import tornadofx.*

open class DocumentPaneFragment : Fragment() {

    val df = params[DocFilter::class.simpleName] as? DocFilter

    val tw = tableview(Facade.documentsByFilter(df).observable()) {
        prefHeight = Options.prefTableHeight
        column<Document, String>(Messages.Typ.cm()) { it ->
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
                action {
                    if (Facade.transactionsByFilter(TransactionFilter(doc = selectedItem)).isEmpty())
                        find<DocumentDeleteDialog>(params = mapOf("doc" to selectedItem)).openModal()
                    else error(Messages.Doklad_je_pouzit_v_transakci.cm())
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
                            find<TransactionsPaneFragment>(
                                    mapOf("tf" to TransactionFilter(doc = selectedItem))).root)

                }
            }*/
        }
        smartResize()
    }

    override val root = DocumentPane(df, tw)

}