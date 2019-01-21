package acc.richclient.controller

import acc.richclient.views.dialogs.AccountAbstractDialog
import acc.richclient.views.dialogs.DialogMode
import acc.richclient.views.dialogs.DocumentCreateDialog
import acc.richclient.views.panes.DocumentView
import acc.richclient.views.PaneTabs
import acc.util.Messages
import tornadofx.*


object DocumentsShowAction : AbstrAction() {

    override val name: String
        get() = Messages.Zobraz_doklady.cm()

    override fun execute() {
        PaneTabs.addTab(Messages.Doklady.cm(), find<DocumentView>().root)
    }

}