package acc.richclient.controller.actions

import acc.richclient.MainWindow
import acc.richclient.dialogs.BalanceCreateDialog
import acc.richclient.dialogs.PrinterDialog
import acc.util.AccException
import acc.util.Messages


object BalanceCreateAction  : AbstrAction() {
    override val name: String
        get() = Messages.Vytvor_rozvahu.cm()
    override fun execute() {
        try {
            BalanceCreateDialog().execute()
        } catch (ex: AccException) {
            MainWindow.showException(ex)
        }

    }


}
object PrintBalanceAction : AbstrAction() {
    override val name: String
        get() = Messages.Tisk_rozvahy.cm()

    override fun execute() {
        try {
            PrinterDialog().execute()
        } catch (ex: AccException) {
            MainWindow.showException(ex)
        }

    }

}
