package acc

import acc.richclient.dialogs.ConfigInitDialog
import acc.util.accError
import javafx.application.Application
import javafx.application.Platform
import tornadofx.*

class AccApp : App(ConfigInitDialog::class, Styles::class) {
    init {
        Platform.runLater {
            Thread.setDefaultUncaughtExceptionHandler { _, ex -> accError(ex) }
        }
        reloadStylesheetsOnFocus()
    }
}


fun main(args: Array<String>) {
    Application.launch(AccApp::class.java, *args)
}

