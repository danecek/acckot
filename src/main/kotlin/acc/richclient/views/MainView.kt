package acc.richclient.views

import tornadofx.*


class MainView : View("Metoccounting") {

    override val root = borderpane {
        top<AccMenuBar>()
        center<PaneTabs>()
        primaryStage.centerOnScreen()
        primaryStage.isResizable = true
        primaryStage.height = 1000.0
        primaryStage.width = 2000.0

    }

}


