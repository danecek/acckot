package acc.richclient.controller.actions

import acc.richclient.MainWindow
import acc.richclient.dialogs.AccountDialog
import acc.richclient.dialogs.DialogMode
import acc.richclient.dialogs.DocumentDialog
import acc.richclient.panes.DocumentPane
import acc.richclient.views.PaneTabs
import acc.util.Messages
import tornadofx.*


object DocumentsShowAction : AbstrAction() {

    override val name: String
        get() = Messages.Zobraz_doklady.cm()

    override fun execute() {
        PaneTabs.addTab(Messages.Doklady.cm(), find<DocumentPane>().root)
    }

}

object DocumentCreateAction : AbstrAction() {
    override val name: String
        get() = Messages.Vytvor_doklad.cm()

    override fun execute() {
        find<DocumentDialog>(params = mapOf(
                AccountDialog::mode to DialogMode.CREATE)).openModal()
    }

}

object DocumentDeleteAction : AbstrAction() {
    override val name: String
        get() = Messages.Zrus_doklad.cm()

    override fun execute() {
/*        MainWindow.instance.getSelectedTab(Messages.Doklady.cm())
                .ifPresent { ap ->
                    (ap as DocumentPane).selected
                            .ifPresent { d -> DocumentDeleteDialog(d).execute() }
                }*/

    }

}

object DocumentUpdateAction : AbstrAction() {
    override val name: String
        get() = Messages.Zmen_doklad.cm()

    override fun execute() {
        val oap = MainWindow.instance.getSelectedTab(Messages.Doklady.cm())
        oap.ifPresent { ap ->
            val dp = (ap as DocumentPane).selected
      //      dp.ifPresent { d -> DocumentUpdateDialog(d).execute() }
        }
    }


}
