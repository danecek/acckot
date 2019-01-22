package acc

import acc.richclient.views.MainView
import javafx.application.Application
import tornadofx.*

class AccApp: App(MainView::class, Styles::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}

fun main(args: Array<String>) {
    Application.launch(AccApp::class.java, *args)
}

