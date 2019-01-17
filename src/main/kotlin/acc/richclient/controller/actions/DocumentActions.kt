package acc.richclient.controller.actions

import acc.richclient.dialogs.AccountAbstractDialog
import acc.richclient.dialogs.DialogMode
import acc.richclient.dialogs.DocumentCreateDialog
import acc.richclient.panes.DocumentView
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

object DocumentCreateAction : AbstrAction() {
    override val name: String
        get() = Messages.Vytvor_doklad.cm()

    override fun execute() {
        find<DocumentCreateDialog>(params = mapOf(
                AccountAbstractDialog::mode to DialogMode.CREATE)).openModal()
    }

}

object DocumentUpdateAction : AbstrAction() {
    override val name: String
        get() = Messages.Zmen_doklad.cm()

    override fun execute() {
        val doc = PaneTabs.selectedDocument
        find<DocumentCreateDialog>(params = mapOf(
                AccountAbstractDialog::mode to DialogMode.UPDATE,
                "doc" to doc)).openModal()
    }

}

object DocumentDeleteAction : AbstrAction() {
    override val name: String
        get() = Messages.Zrus_doklad.cm()

    override fun execute() {
        val doc = PaneTabs.selectedDocument
        find<DocumentCreateDialog>(params = mapOf(
                AccountAbstractDialog::mode to DialogMode.DELETE,
                "doc" to doc)).openModal()
    }

}


