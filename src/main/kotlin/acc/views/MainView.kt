package acc.views

import acc.richclient.controller.menus.AccMenuBar
import javafx.application.Platform
import tornadofx.*


object ExitAction : () -> Unit {
    override fun invoke() {
        Platform.exit()
    }
}

object ShowTransaction : () -> Unit {
    override fun invoke() = acc.richclient.dialogs.AccountCreateDialog().execute()
}


class MainView : View("Metaccounting") {

    override val root = borderpane {
        top<AccMenuBar>()
        center<PaneTabs>()
        bottom {
            button("b") {
                action {
                    dialog("ddddd", owner = null) {
                        field ("Name"){
                            textfield ("napis")

                        }
                        button ("Close"){
                            action { stage.close() }
                        }

                    }


                }

            }
        }

    }
}


