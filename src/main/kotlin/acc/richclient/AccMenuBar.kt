package acc.richclient

import acc.model.DocType
import acc.richclient.controller.*
import acc.richclient.dialogs.DialogMode
import acc.richclient.dialogs.accounts.AccountCreateDialog
import acc.util.Messages
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import tornadofx.*


class AccMenuBar : View() {
    private fun Menu.add(a: AbstrAction) {
        val mi = MenuItem(a.name)
        mi.onAction = EventHandler(a)
        items.add(mi)
    }

    override val root = menubar {
        menu(Messages.File.cm()) {
            item(Messages.Konec.cm()) {
                action {
                    Platform.exit()
                }
            }
        }
        menu(Messages.Ucty.cm()) {
            item(Messages.Vytvor_ucet.cm()) {
                action {
                    find<AccountCreateDialog>(params = mapOf(
                            "mode" to DialogMode.CREATE)).openModal()
                }
            }
        }
        menu(Messages.Doklady.cm()) {
            add(ClearDocFilterAction)
            add(OpenDocFilterDialogAction)
            add(ShowUnpaidInvocesAction)
            menu(Messages.Vytvor.cm()) {
                item(Messages.Fakturu.cm()) {
                    action {
                        openDocCreateDialog(DocType.INVOICE)
                    }
                }
                item(Messages.Bankovni_vypis.cm()) {
                    action {
                        openDocCreateDialog(DocType.BANK_STATEMENT)
                    }
                }
                item(Messages.Prijmovy_doklad.cm()) {
                    action {
                        openDocCreateDialog(DocType.INCOME)
                    }
                }
                item(Messages.Vydajovy_doklad.cm()) {
                    action {
                        openDocCreateDialog(DocType.OUTCOME)
                    }
                }
                item(Messages.Ucetni_udalost.cm()) {
                    action {
                        openDocCreateDialog(DocType.EVENT)
                    }
                }
            }
        }
        menu(Messages.Transakce.cm())
        {
            add(ClearTransFilterAction)
            add(OpenTransFilterDialogAction)
            add(TransactionCreateAction)
        }
        menu(Messages.Vystup.cm())
        {
            add(BalanceCreateAction)
            add(IncomeCreateAction)
            item("test")  {
                action {
                    throw RuntimeException("test")
                }

            }
        //    add(PrintBalanceAction)

        }
    }
}

