package acc.richclient.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.richclient.controller.openAccountDeleteDialog
import acc.richclient.controller.openAccountUpdateDialog
import acc.util.Messages
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.scene.control.TableView
import tornadofx.*

open class AccountsView : View(Messages.Ucty.cm()) {

    override val closeable = SimpleBooleanProperty(false)

    private val saccw = 5
    private val analw = 5
    private val namew = 30
    private val madatidalw = 10

    fun maDati(acc: AnalAcc) =
            if (acc.isBalanced && acc.initAmount >= 0) acc.initAmount.toString()
            else ""

    fun dal(acc: AnalAcc) =
            if (acc.isBalanced && acc.initAmount < 0) (-acc.initAmount).toString()
            else ""

    private val tableView: TableView<AnalAcc> = tableview(mutableListOf<AnalAcc>().observable()) {
        column<AnalAcc, String>(Messages.Synteticky_ucet.cm()) {
            ReadOnlyObjectWrapper(it.value.syntAccount.number)
        }.weightedWidth(saccw)
                .cellDecorator {
                    tooltip {
                       text = items[index].syntAccount.name
                    }
                }
        readonlyColumn(Messages.Analytika.cm(), AnalAcc::anal).weightedWidth(analw)
        readonlyColumn(Messages.Jmeno.cm(), AnalAcc::name).weightedWidth(namew)
        nestedColumn(Messages.Pocatecni_stav.cm()) {
            column<AnalAcc, String>(Messages.Ma_dati.cm()) {
                ReadOnlyObjectWrapper(maDati(it.value))
            }.weightedWidth(madatidalw)

            column<AnalAcc, String>(Messages.Dal.cm()) {
                ReadOnlyObjectWrapper(dal(it.value))
            }.weightedWidth(madatidalw)

        }
        contextmenu {
            item(Messages.Zmen_ucet.cm()).action {
                openAccountUpdateDialog(selectedItem!!)
            }
            with(item(Messages.Zrus_ucet.cm())) {
                action {
                    onShowing = EventHandler {
                        isDisable = Facade.accountIsUsed(selectedItem)
                    }
                    openAccountDeleteDialog(selectedItem!!)
                }
                setOnShown {
                    runAsync {
                        Facade.accountIsUsed(selectedItem)
                    } ui {
                        this@contextmenu.items[1].isDisable = it
                    }
                }
            }

/*            zu.enableWhen {
                println(Facade.transactionsByFilter(TransactionFilter(acc = selectedItem)))
                SimpleBooleanProperty(!Facade.accountIsUsed(selectedItem))
            }*/
        }
        smartResize()
    }

    override val root = tableView

    init {
        refresh()
    }

    fun refresh() {
        tornadofx.runAsync {
            Facade.allAccounts
        } ui {
            tableView.items.setAll(it)
        }

    }


}