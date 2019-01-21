package acc.richclient.views.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.richclient.views.dialogs.AccountDeleteDialog
import acc.richclient.views.dialogs.AccountUpdateDialog
import acc.util.Messages
import javafx.scene.control.TableView
import tornadofx.*

class AccountView : View() {

    val tw: TableView<AnalAcc> = tableview<AnalAcc>(Facade.allAccounts.observable()) {
        readonlyColumn(Messages.Synteticky_ucet.cm(), AnalAcc::syntAccount)
        readonlyColumn(Messages.Analytika.cm(), AnalAcc::anal)
        readonlyColumn(Messages.Jmeno.cm(), AnalAcc::fullName)
        contextmenu {
            item(Messages.Zmen_ucet.cm()).action {
                find<AccountUpdateDialog>(params = mapOf("acc" to selectedItem)).openModal()
            }
            item(Messages.Zrus_ucet.cm()).action {
                find<AccountDeleteDialog>(params = mapOf("acc" to selectedItem)).openModal()
            }
        }
    }

    override val root = AccountPane(tw)
}