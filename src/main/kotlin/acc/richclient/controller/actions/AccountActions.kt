package acc.richclient.controller.actions

import acc.richclient.dialogs.AccountDialog
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.AccountPane
import acc.richclient.views.PaneTabs
import acc.util.Messages
import javafx.scene.control.Alert
import tornadofx.*


object AccountsShowAction : AbstrAction() {

    override val name = Messages.Zobraz_ucty.cm()

    override fun execute() {
        PaneTabs.addTab(Messages.Ucty.cm(), find<AccountPane>().root)// AccountsPane())
    }

}

object AccountCreateAction : AbstrAction() {

    override val name = Messages.Vytvor_ucet.cm()

    override fun execute() {
        find(AccountDialog::class, params = mapOf(
                AccountDialog::mode to DialogMode.CREATE)).openModal()
    }

}

object AccountDeleteAction : AbstrAction() {

    override val name = Messages.Zrus_ucet.cm()

    override fun execute() {
        val accPane = find<AccountPane>()
        val acc =  accPane.selected
        if (acc != null)
            tornadofx.find(AccountDialog::class, params = mapOf(
                    AccountDialog::mode to DialogMode.DELETE,
                    AccountDialog::acc to acc
            )).openModal()
        else alert(Alert.AlertType.INFORMATION, header = Messages.neni_vybran_zadny_ucet.cm())
    }
}

