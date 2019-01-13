package acc.richclient.controller.actions

import acc.richclient.MainWindow
import acc.richclient.dialogs.AccDeleteDialog
import acc.richclient.dialogs.AccountCreateDialog
import acc.richclient.dialogs.AccountDialog
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.AccountsPane
import acc.util.AccException
import acc.util.Messages
import acc.richclient.views.PaneTabs
import tornadofx.*


object AccountsShowAction : AbstrAction() {

    override val name = Messages.Zobraz_ucty.cm()

    override fun execute() {
        PaneTabs.addTab(Messages.Ucty.cm(), AccountsPane())
    }

}

object AccountCreateAction : AbstrAction() {

    override val name = Messages.Vytvor_ucet.cm()

    override fun execute() {
        find(AccountDialog::class, params= mapOf(AccountDialog::mode to DialogMode.CREATE)).openModal()
    }

}

object AcountDeleteAction : AbstrAction() {
    override val name = Messages.Zrus_ucet.cm()

    override fun execute() {
        MainWindow.getInstance().accountPane
                .ifPresent { ap: AccountsPane ->
                    ap.selected()
                            .ifPresent { ap1 ->
                                try {
                                    AccDeleteDialog(ap1).execute()
                                } catch (ex: AccException) {
                                    MainWindow.showException(ex)
                                }


                            }
                }
    }


}
