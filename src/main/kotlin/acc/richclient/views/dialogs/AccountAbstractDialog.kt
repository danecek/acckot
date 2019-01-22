package acc.richclient.views.dialogs

import acc.model.AccGroup
import acc.model.AnalAcc
import acc.model.Osnova
import acc.richclient.views.PaneTabs
import acc.util.Messages
import javafx.beans.property.StringProperty
import tornadofx.*
import java.util.regex.Pattern

class AccountDialogModel(acc: AnalAcc?) : ItemViewModel<AnalAcc>(acc) {
    val id = bind(AnalAcc::id)
    val group = bind(AnalAcc::parent)
    val anal  = bind(AnalAcc::anal)
    val name = bind(AnalAcc::name)
}

abstract class AccountAbstractDialog(val mode: DialogMode) : Fragment() {

    val acc: AnalAcc? = params["acc"] as? AnalAcc
    val model = AccountDialogModel(acc)

    init {
        when (mode) {
            DialogMode.CREATE -> title = Messages.Vytvor_ucet.cm()
            DialogMode.UPDATE -> title = Messages.Zmen_ucet.cm()
            DialogMode.DELETE -> title = Messages.Zrus_ucet.cm()
        }
    }

    override val root = form {
        fieldset {
            field(Messages.Synteticky_ucet.cm()) {
                combobox<AccGroup>(model.group, Osnova.syntAccounts()) {
                    isDisable = mode != DialogMode.CREATE
                    validator {
                        if (it == null) error(Messages.prazdna_skupina.cm())
                        else null
                    }
                }
            }

            field(Messages.Analytika.cm()) {
                textfield(model.anal) {
                    isDisable = mode != DialogMode.CREATE
                    validator {
                        if (it == null || !Pattern.matches("\\d\\d\\d", it))
                            error(Messages.Analytika_musi_byt_tri_cislice.cm())
                        else null
                    }
                }
            }
            field(Messages.Nazev.cm()) {
                textfield(model.name) {
                    isDisable = mode == DialogMode.DELETE
                }
            }
        }
        buttonbar {
            button(Messages.Potvrd.cm()) {
                enableWhen(model.valid)
                action {
                    ok()
                    PaneTabs.refreshAccountPane()
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
    abstract val ok: () -> Unit
}


