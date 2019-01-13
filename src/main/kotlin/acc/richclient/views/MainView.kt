package acc.richclient.views

import acc.richclient.controller.menus.AccMenuBar
import tornadofx.*



class MainView : View("Metoccounting") {

    override val root = borderpane {
        top<AccMenuBar>()
        center<PaneTabs>()
        primaryStage.centerOnScreen()
        primaryStage.minHeight = 1000.0
       primaryStage.minWidth = 2000.0

    }
}


