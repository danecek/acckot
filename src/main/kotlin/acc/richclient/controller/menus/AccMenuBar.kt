package acc.richclient.controller.menus

import acc.richclient.controller.actions.*
import acc.util.Messages
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

    override val root = menubar {
        menu(Messages.File.cm()) {
            add(Exit)
        }
        menu(Messages.Ucty.cm()) {
            add(AccountsShowAction)
            add(AccountCreateAction)
            add(AccountDeleteAction)
        }

        menu(Messages.Doklady.cm())
        {
            add(DocumentsShowAction)
            add(DocumentCreateAction)
            add(DocumentUpdateAction)
            add(DocumentDeleteAction)
        }

        menu(Messages.Transakce.cm())
        {
            add(TransactionsShowAction)
            add(TransactionCreateAction)
            add(TransactionUpdateAction)
            add(TransactionDeleteAction)
            add(Separator())
            add(InitsShowAction)
            add(InitCreateAction)
        }

        menu(Messages.Rozvaha.cm())
        {
            add(PrintBalanceAction)
            add(BalanceCreateAction)
        }
    }
}

