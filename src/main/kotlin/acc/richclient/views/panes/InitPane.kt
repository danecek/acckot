package acc.richclient.views.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.Init
import acc.util.Messages
import acc.util.withColon
import javafx.scene.control.TableView
import javafx.scene.control.TitledPane
import tornadofx.*

fun String.t(acc : AnalAcc?) =  if (acc!=null) this+
       Messages.pro_ucet.cm().withColon + acc.numberName else ""

class InitPane(private val content: TableView<Init>,
               val acc: AnalAcc?) : TitledPane(Messages.Pocatecni_stavy.cm().t(acc),
        content) {

    fun refresh() {
        content.items.setAll(Facade.getInits(acc).observable())
    }

}
