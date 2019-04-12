package acc

import acc.richclient.dialogs.ConfigInitDialog
import javafx.application.Application
import tornadofx.*

class AccApp: App(ConfigInitDialog::class, Styles::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}

fun x() {}

fun main(args: Array<String>) {
    Application.launch(AccApp::class.java, *args)
}

