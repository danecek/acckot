package acc.richclient.controller.actions

import acc.richclient.dialogs.InitCreateDialog
import acc.richclient.dialogs.InitShowDialog
import acc.util.Messages
import javafx.stage.StageStyle
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