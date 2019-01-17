package acc.richclient.dialogs

import acc.model.AccGroup
import acc.model.AnalAcc
import acc.model.Osnova
import acc.richclient.views.PaneTabs
import acc.util.Messages
import tornadofx.*

class AccountDialogModel(acc: AnalAcc?) : ItemViewModel<AnalAcc>(acc) {
    val id = bind(AnalAcc::id)
    val group = bind(AnalAcc::parent)
    val anal = bind(AnalAcc::anal)
    val name = bind(AnalAcc::name)
}

abstract class AccountAbstractDialog : Fragment() {

    val mode: DialogMode by params
    val acc: AnalAcc by params
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
                val gcb = combobox<AccGroup>(model.group, Osnova.syntAccounts())
                gcb.isDisable = mode == DialogMode.DELETE
                gcb.validator {
                    if (it == null) error(Messages.prazdna_skupina.cm()) else null
                }
            }

            field(Messages.Analytika.cm()) {
                val atf = textfield(model.anal)
                atf.isDisable = mode == DialogMode.DELETE
                atf.validator {
                    if (it.isNullOrBlank()) error(Messages.analytika_musi_byt_tri_cislice.cm()) else null
                }
            }
            field(Messages.Nazev.cm()) {
                textfield(model.name).isDisable = mode == DialogMode.DELETE
            }
        }
        buttonbar {
            button(Messages.Potvrd.cm()) {
                enableWhen(model.anal.isNotBlank())
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


