package acc.richclient

import acc.Options
import acc.richclient.controller.OpenDocFilterDialogAction
import acc.richclient.controller.OpenTransFilterDialogAction
import acc.richclient.dialogs.ConfigInitDialog
import acc.richclient.panes.PaneTabs
import acc.util.Messages
import tornadofx.*


class MainView : View(Messages.Ucetnictvi.cm().plus(" ").plus(ConfigInitDialog.year)) {

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


