package acc.richclient.controller.actions

import acc.richclient.MainWindow
import acc.richclient.dialogs.DocumentCreateDialog
import acc.richclient.dialogs.DocumentDeleteDialog
import acc.richclient.dialogs.DocumentUpdateDialog
import acc.richclient.panes.DocumentPane
import acc.util.Messages


object DocumentsShowAction : AbstrAction() {

    override val name: String
        get() = Messages.Zobraz_doklady.cm()

    override fun execute() {
        DocumentPane().addToMain()
    }

}

object DocumentCreateAction : AbstrAction() {
    override val name: String
        get() = Messages.Vytvor_doklad.cm()

    override fun execute() {
        DocumentCreateDialog().execute()
    }

}

object DocumentDeleteAction : AbstrAction() {
    override val name: String
        get() = Messages.Zrus_doklad.cm()

    override fun execute() {
        MainWindow.instance.getSelectedTab(Messages.Doklady.cm())
                .ifPresent { ap ->
                    (ap as DocumentPane).selected
                            .ifPresent { d -> DocumentDeleteDialog(d).execute() }
                }

    }


}

object DocumentUpdateAction : AbstrAction() {
    override val name: String
        get() = Messages.Zmen_doklad.cm()

    override fun execute() {
        val oap = MainWindow.instance.getSelectedTab(Messages.Doklady.cm())
        oap.ifPresent { ap ->
            val dp = (ap as DocumentPane).selected
            dp.ifPresent { d -> DocumentUpdateDialog(d).execute() }
        }
    }


}
