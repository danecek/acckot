package acc

import acc.richclient.dialogs.ConfigInitDialog
import acc.util.accFail
import javafx.application.Application
import javafx.application.Platform
import tornadofx.*
import java.nio.file.Paths


class AccApp : App(ConfigInitDialog::class, Styles::class) {

    override val configBasePath= Paths
            .get(System.getProperty("user.home"), ".ucetnictvi")

    init {
        Platform.runLater {
            Thread.setDefaultUncaughtExceptionHandler { _, ex -> accFail(ex) }
        }
        reloadStylesheetsOnFocus()
    }
}

fun main(args: Array<String>) {
    Application.launch(AccApp::class.java, *args)
}

