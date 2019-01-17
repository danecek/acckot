package acc.richclient.controller.actions

import acc.richclient.dialogs.AccountAbstractDialog
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.AccountsView
import acc.richclient.views.PaneTabs
import acc.util.Messages
import tornadofx.*


object AccountsShowAction : AbstrAction() {

    override val name = Messages.Zobraz_ucty.cm()

    override fun execute() {
        PaneTabs.addTab(Messages.Ucty.cm(), find<AccountsView>().root)
    }

}

object AccountCreateAction : AbstrAction() {

    override val name = Messages.Vytvor_ucet.cm()

    override fun execute() {
        find(AccountAbstractDialog::class, params = mapOf(
                AccountAbstractDialog::mode to DialogMode.CREATE)).openModal()
    }

}

object AccountUpdateAction : AbstrAction() {

    override val name = Messages.Zrus_ucet.cm()

    override fun execute() {
        val acc = PaneTabs.selectedAccount
        find(AccountAbstractDialog::class, params = mapOf(
                AccountAbstractDialog::mode to DialogMode.UPDATE,
                "acc" to acc)).openModal()
    }
}


object AccountDeleteAction : AbstrAction() {

    override val name = Messages.Zrus_ucet.cm()

    override fun execute() {
        val acc = PaneTabs.selectedAccount
        find(AccountAbstractDialog::class, params = mapOf(
                AccountAbstractDialog::mode to DialogMode.DELETE,
                "acc" to acc)).openModal()
    }
}

