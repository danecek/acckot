package acc.richclient.dialogs

import acc.business.Facade
import acc.model.AccGroup
import acc.model.Osnova
import acc.util.Messages
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class AccountDialog : View() {

    val mode: DialogMode by params
    val group = SimpleObjectProperty<AccGroup>()
    val anal = SimpleStringProperty("001")
    val name = SimpleStringProperty("")

    init {
        when (mode) {
            DialogMode.CREATE -> title = Messages.Vytvor_ucet.cm()
            DialogMode.UPDATE -> title = Messages.Zmen_ucet.cm()
            DialogMode.DELETE -> title = Messages.Zrus_ucet.cm()
        }
    }

    override val root = form {
        fieldset {
            field(Messages.Skupina.cm()) {
                combobox<AccGroup>(group, Osnova.groups)
            }

            field(Messages.Analytika.cm()) {
                textfield(anal)
            }


            field(Messages.Nazev.cm()) {
                textfield(name)
            }
        }
        buttonbar {
            button(Messages.Potvrd.cm()) {
                action {
                    when (mode) {
                        DialogMode.CREATE -> Facade.createAccount(group.value, anal.value, name.value)
                        //        DialogMode.UPDATE -> Facade.updateAccount(group.value, anal.value, name.value)
                        //        DialogMode.DELETE -> Facade.deleteAccount(id)
                    }
                    close()
                }

            }
            button(Messages.Zrus.cm()) {
                action {
                    close()
                }
            }
        }


    }
}