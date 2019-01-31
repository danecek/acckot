package acc.richclient

import acc.Options
import acc.richclient.controller.ShowAccountsAction
import acc.richclient.controller.ShowDocumentsAction
import acc.richclient.controller.ShowTransactionsAction
import acc.util.Messages
import tornadofx.*


class MainView : View(Messages.Ucetnictvi.cm().plus(" ").plus(Options.year)) {

    override val root = borderpane {

        top<AccMenuBar>()
        center<PaneTabs>()
        bottom {
            toolbar {
                button(ShowDocumentsAction.name) {
                    enableWhen(ShowDocumentsAction)
                    action {
                        ShowDocumentsAction.execute()
                    }
                }
                button(ShowAccountsAction.name) {
                    enableWhen(ShowAccountsAction)
                    action { ShowAccountsAction.execute() }

                }
                button(ShowTransactionsAction.name) {
                    enableWhen(ShowTransactionsAction)
                    action { ShowTransactionsAction.execute() }

                }
            }
        }
    }
}


