package acc.richclient

import acc.Options
import acc.richclient.controller.OpenDocFilterDialogAction
import acc.richclient.controller.OpenTransFilterDialogAction
import acc.util.Messages
import tornadofx.*


class MainView : View(Messages.Ucetnictvi.cm().plus(" ").plus(Options.year)) {

    override val root = borderpane {

        top<AccMenuBar>()
        center<PaneTabs>()
        bottom {
            toolbar {
                button(OpenDocFilterDialogAction.name) {
                    enableWhen(OpenDocFilterDialogAction)
                    action {
                        OpenDocFilterDialogAction.execute()
                    }
                }
                button(OpenTransFilterDialogAction.name) {
                    enableWhen(OpenTransFilterDialogAction)
                    action { OpenTransFilterDialogAction.execute() }

                }
            }
        }
    }
}


