package acc.richclient.controller.menus

import acc.richclient.controller.actions.*
import acc.util.Messages
import javafx.event.EventHandler
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import tornadofx.View
import tornadofx.menu
import tornadofx.menubar


class AccMenuBar : View() {
    private fun Menu.add(a: AbstrAction) {
        val mi = MenuItem(a.name)
        mi.onAction = EventHandler(a)
        items.add(mi)
    }

    override val root = menubar {
        menu(Messages.File.cm()) {
            add(Exit)
            add(TestFragment)

        }
        menu(Messages.Ucty.cm()) {
            add(AccountsShowAction)
            add(AcountCreateAction)
        //    add(AcountUpdateAction)
            add(AcountDeleteAction)
        }

        menu(Messages.Doklady.cm()) {
            add(DocumentsShowAction)
            add(DocumentCreateAction)
            add(DocumentUpdateAction)
            add(DocumentDeleteAction)
        }

        menu(Messages.Transakce.cm()) {
            add(TransactionsShowAction)
            add(TransactionCreateAction)
            add(UpdateTransactionAction)
            add(TransactionDeleteAction)
            add(InitsShowAction)
            add(InitCreateAction)
        }

        menu(Messages.Rozvaha.cm()) {
            add(PrintBalanceAction)
            add(BalanceCreateAction)
        }
    }
}

