package acc.richclient.controller

import acc.richclient.views.dialogs.BalanceShowDialog
import acc.util.Messages
import tornadofx.*


object BalanceCreateAction : AbstrAction() {
    override val name: String
        get() = Messages.Vytvor_rozvahu.cm()

    override fun execute() {
        find<BalanceShowDialog>().openModal()
    }

}


object PrintBalanceAction : AbstrAction() {
    override val name: String
        get() = Messages.Tisk_rozvahy.cm()

    override fun execute() {


    }

}
