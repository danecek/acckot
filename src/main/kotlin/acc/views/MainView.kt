package acc.views

import acc.richclient.controller.menus.AccMenuBar
import acc.richclient.panes.MyDialog
import javafx.application.Platform
import javafx.stage.StageStyle
import tornadofx.*



class MainView : View("Metaccounting") {

    override val root = borderpane {
        top<AccMenuBar>()
        center<PaneTabs>()
        bottom {
            button("Dialog") {
                action {find<MyDialog>().openModal(StageStyle.DECORATED)}
            }
        }

    }
}


