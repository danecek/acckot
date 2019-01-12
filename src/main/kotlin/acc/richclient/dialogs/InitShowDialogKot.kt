package acc.richclient.dialogs

import acc.business.Facade
import acc.richclient.panes.InitsPane
import acc.richclient.panes.MyDialog
import acc.util.AccException
import acc.util.Messages
import acc.views.PaneTabs
import javafx.collections.FXCollections
import javafx.collections.FXCollections.observableArrayList
import javafx.scene.control.ButtonBar
import tornadofx.*

class InitShowDialogKot : MyDialog(Messages.Zobraz_pocatecni_stavy.cm()) {

    private var accCB: AccountCB? = AccountCB(observableArrayList(Facade.allAccounts))
    val bb = buttonbar {
        button("Ok") {
            PaneTabs.addTab(Messages.Pocatecni_stavy.cm(),
                    InitsPane(accCB!!.optAccount))
            close()
        }
    }

    fun ButtonBar.genButtons() {

    }

    override val cont = form {
        fieldset("Personal Info") {
            field("First Name") {
                textfield()
            }
            field("Last Name") {
                textfield()
            }
            field("Birthday") {
                datepicker()
            }
        }

        buttonbar(op = this::genButtons)
    }


    @Throws(AccException::class)
    fun ok() {
        PaneTabs.addTab(Messages.Pocatecni_stavy.cm(),
                InitsPane(accCB!!.optAccount))
    }

    fun validate() {}
}
