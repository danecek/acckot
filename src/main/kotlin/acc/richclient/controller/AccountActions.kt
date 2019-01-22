package acc.richclient.controller

import acc.richclient.views.PaneTabs
import acc.richclient.views.dialogs.AccountCreateDialog
import acc.richclient.views.dialogs.DialogMode
import acc.richclient.views.panes.AccountView
import acc.util.Messages
import tornadofx.*


object AccountsShowAction : AbstrAction() {

    override val name = Messages.Zobraz_ucty.cm()

    override fun execute() {
        if (PaneTabs.accountPane == null)
            PaneTabs.addTab(Messages.Ucty.cm(), find<AccountView>().root)
    }

}

object AccountCreateAction : AbstrAction() {

    override val name = Messages.Vytvor_ucet.cm()

    override fun execute() {
        find<AccountCreateDialog>(params = mapOf(
                "mode" to DialogMode.CREATE)).openModal()
    }

}

