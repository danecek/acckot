package acc.richclient.views.dialogs

import acc.business.Facade
import acc.model.AnalAcc
import acc.richclient.views.panes.InitFragment
import acc.richclient.views.PaneTabs
import acc.util.Messages
import acc.util.withColon
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class InitShowDialog : Fragment() {

    val acc = SimpleObjectProperty<AnalAcc>()

    override val root =
            form {
                title = Messages.Zobraz_pocatecni_stavy.cm()
                fieldset {
                    field(Messages.Pro_ucet.cm().withColon) {
                        combobox(acc, Facade.allAccounts)
                    }

                }
                buttonbar {
                    button(Messages.Potvrd.cm()) {
                        action {
                            PaneTabs.addTab(Messages.Pocatecni_stavy.cm(),
                                    find<InitFragment>(mapOf("acc" to acc)).root)
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