package acc.richclient.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.richclient.dialogs.AccountDeleteDialog
import acc.richclient.dialogs.AccountUpdateDialog
import acc.util.Messages
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import tornadofx.*

class AccountView : View() {

    fun maDati(acc: AnalAcc) =
            if (acc.balanced && acc.initAmount >= 0) acc.initAmount.toString()
            else ""

    fun dal(acc: AnalAcc) =
            if (acc.balanced && acc.initAmount < 0) (-acc.initAmount).toString()
            else ""

    val tw = tableview(Facade.allAccounts.observable()) {
        readonlyColumn(Messages.Synteticky_ucet.cm(), AnalAcc::syntAccount).pctWidth(20.0)
        readonlyColumn(Messages.Analytika.cm(), AnalAcc::anal).pctWidth(10.0)
        readonlyColumn(Messages.Jmeno.cm(), AnalAcc::name).remainingWidth()
        nestedColumn(Messages.Pocatecni_stav.cm()) {
            column<AnalAcc, String>(Messages.Ma_dati.cm()) {
                SimpleStringProperty(maDati(it.value))
            }.pctWidth(15.0)

            column<AnalAcc, String>(Messages.Dal.cm()) {
                SimpleStringProperty(dal(it.value))
            }.pctWidth(15.0)
        }
        contextmenu {
            item(Messages.Zmen_ucet.cm()).action {
                find<AccountUpdateDialog>(params = mapOf("acc" to selectedItem)).openModal()
            }
            item(Messages.Zrus_ucet.cm()).action {
                if (Facade.accountIsUsed(selectedItem)) {
                    alert(Alert.AlertType.ERROR, Messages.Ucet_je_pouzit_v_transakci.cm())
                } else
                    find<AccountDeleteDialog>(params = mapOf("acc" to selectedItem)).openModal()
            }
        }
        smartResize()
    }

    override val root = AccountPane(tw)
}