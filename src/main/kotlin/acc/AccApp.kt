package acc

import acc.views.MainView
import javafx.application.Application
import tornadofx.App

class AccApp: App(MainView::class, Styles::class)

/**
 * The main method is needed to support the mvn jfx:run goal.
 */
fun main(args: Array<String>) {
    Application.launch(AccApp::class.java, *args)
}

fun start() {


}
