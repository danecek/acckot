package acc.richclient.controller

import acc.richclient.views.dialogs.InitCreateDialog
import acc.richclient.views.dialogs.InitShowDialog
import acc.util.Messages
import tornadofx.*

object InitCreateAction : AbstrAction() {

    override val name: String
        get() = Messages.Nastav_pocatecni_stav.cm()

    override fun execute() {
        find<InitCreateDialog>().openModal()
    }

}

object InitsShowAction : AbstrAction() {
    override val name: String
        get() = Messages.Zobraz_pocatecni_stavy.cm()

    override fun execute() {
        find<InitShowDialog>().openModal()
    }

}