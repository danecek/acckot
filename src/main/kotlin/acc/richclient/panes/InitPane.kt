package acc.richclient.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.Init
import acc.util.Messages
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*

class InitPane(private val content: TableView<Init>,
               val acc: AnalAcc?) : TitledPane(Messages.Pocatecni_stavy.cm(), content) {

    fun refresh() {
        content.items.setAll(Facade.getInits(acc).observable())
    }

    val selected: Init?
        get() = content.selectedItem

}
