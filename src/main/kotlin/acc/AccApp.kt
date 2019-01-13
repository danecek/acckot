package acc

import acc.richclient.views.MainView
import javafx.application.Application
import tornadofx.App

class AccApp: App(MainView::class, Styles::class)




fun main(args: Array<String>) {
    Application.launch(AccApp::class.java, *args)
}

