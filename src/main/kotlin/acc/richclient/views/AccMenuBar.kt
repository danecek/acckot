package acc.richclient.views

import acc.business.Facade
import acc.model.DocumentType
import acc.richclient.controller.*
import acc.richclient.views.dialogs.DocumentCreateDialog
import acc.richclient.views.panes.AccountView
import acc.util.Messages
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.control.Separator
import tornadofx.*


class AccMenuBar : View() {
    private fun Menu.add(a: AbstrAction) {
        val mi = MenuItem(a.name)
        mi.onAction = EventHandler(a)
        items.add(mi)
    }

    init {
        find<PaneTabs>().root.tabs.addListener(
                AccountPaneClosed
        )

    }

    object AccountPaneClosed : SimpleBooleanProperty(PaneTabs.accountPane == null), InvalidationListener {
        override fun invalidated(observable: Observable?) {
            value = PaneTabs.accountPane == null
        }

    }

    fun openDocCreateDialog(docType: DocumentType) {
        val id = Facade.genDocumentId(docType)
        find<DocumentCreateDialog>(params = mapOf("docType" to docType, "id" to id)).openModal()
    }

    override val root = menubar {
        menu(Messages.File.cm()) {
            add(Exit)
        }
        menu(Messages.Ucty.cm()) {
            item(Messages.Zobraz_ucty.cm()) {
                enableWhen(AccountPaneClosed)
                action {
                    if (PaneTabs.accountPane == null)
                        PaneTabs.addTab(Messages.Ucty.cm(), tornadofx.find<AccountView>().root)
                }
            }
            add(AccountCreateAction)
        }
        menu(Messages.Doklady.cm())
        {
            add(DocumentsShowAction)
            menu(Messages.Zadej.cm()) {
                item(Messages.Fakturu.cm()) {
                    action {
                        openDocCreateDialog(DocumentType.INVOICE)
                    }
                }
                item(Messages.Bankovni_vypis.cm()) {
                    action {
                        openDocCreateDialog(DocumentType.BANK_STATEMENT)
                    }
                }
                item(Messages.Prijmovy_doklad.cm()) {
                    action {
                        openDocCreateDialog(DocumentType.INCOME)
                    }
                }
                item(Messages.Vydajovy_doklad.cm()) {
                    action {
                        openDocCreateDialog(DocumentType.OUTCOME)
                    }
                }
                item(Messages.Ucetni_udalost.cm()) {
                    action {
                        openDocCreateDialog(DocumentType.EVENT)
                    }
                }
            }
        }
        menu(Messages.Transakce.cm())
        {
            add(TransactionsShowAction)
         //   add(TransactionCreateAction)
            add(Separator())
            add(InitsShowAction)
            add(InitCreateAction)
        }
        menu(Messages.Rozvaha.cm())
        {
            add(BalanceCreateAction)
            add(PrintBalanceAction)

        }
    }
}

