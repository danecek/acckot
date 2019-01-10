package acc

import acc.views.MainView
import javafx.application.Application
import tornadofx.App
import tornadofx.Stylesheet.Companion.label

class AccApp: App(MainView::class, Styles::class)




fun main(args: Array<String>) {
    Application.launch(AccApp::class.java, *args)
}

