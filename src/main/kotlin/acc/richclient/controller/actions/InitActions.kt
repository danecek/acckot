package acc.richclient.controller.actions

import acc.richclient.MainWindow
import acc.richclient.dialogs.CreateInitDialog
import acc.richclient.dialogs.InitShowDialog
import acc.richclient.dialogs.InitShowDialogKot
import acc.util.AccException
import acc.util.Messages
import tornadofx.*

object InitCreateAction  : AbstrAction() {

    override val name: String
        get() = Messages.Nastav_pocatecni_stav.cm()

    override fun execute() {
        try {
            CreateInitDialog().execute()
        } catch (ex: AccException) {
            MainWindow.showException(ex)
        }

    }

}

object InitsShowAction : AbstrAction() {
    override val name: String
        get() = Messages.Zobraz_pocatecni_stavy.cm()
    override fun execute() {
        find<InitShowDialogKot>().openModal()

    }



}