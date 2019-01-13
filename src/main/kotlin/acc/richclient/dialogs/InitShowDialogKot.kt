package acc.richclient.dialogs

import acc.business.Facade
import acc.richclient.panes.InitsPane
import acc.util.AccException
import acc.util.Messages
import acc.richclient.views.PaneTabs
import javafx.collections.FXCollections.observableArrayList
import javafx.scene.control.Label
import javafx.scene.layout.Pane


class InitShowDialogKot : MyDialog(Messages.Zobraz_pocatecni_stavy.cm()) {

    var accCB: AccountCB? = AccountCB(observableArrayList(Facade.allAccounts))

    override fun createCont(): Pane {
        return gp
    }


    init {
        with(gp) {
            add(Label("xxx"), 0, 0)
        }

    }

    @Throws(AccException::class)
    override fun ok() {
        PaneTabs.addTab(Messages.Pocatecni_stavy.cm(),
                InitsPane(accCB!!.optAccount))
    }


}

