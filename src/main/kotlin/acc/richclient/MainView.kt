package acc.richclient

import acc.Options
import acc.util.Messages
import tornadofx.*


class MainView : View(Messages.Ucetnictvi.cm().plus(" ").plus(Options.year)) {

    override val root = borderpane {
        prefWidth = 2000.0
        top<AccMenuBar>()
        center<PaneTabs>()
    }
}


