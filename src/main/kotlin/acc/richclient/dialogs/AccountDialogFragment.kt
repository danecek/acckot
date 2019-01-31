package acc.richclient.dialogs

import acc.Options
import acc.model.AccGroup
import acc.model.AnalAcc
import acc.model.Osnova
import acc.richclient.PaneTabs
import acc.util.Messages
import acc.util.accError
import acc.util.withColon
import javafx.scene.control.Alert
import tornadofx.*
import java.util.regex.Pattern

class AccountDialogModel(acc: AnalAcc?) : ItemViewModel<AnalAcc>(acc) {
    val group = bind(AnalAcc::syntAccount)
    val anal = bind(AnalAcc::anal)
    val name = bind(AnalAcc::name)
    val initAmount = bind(AnalAcc::initAmount)
}

abstract class AccountDialogFragment(val mode: DialogMode) : Fragment() {

    val acc: AnalAcc? = params[AnalAcc::class.simpleName] as? AnalAcc
    val accModel = AccountDialogModel(acc)

    init {
        when (mode) {
            DialogMode.CREATE -> title = Messages.Vytvor_ucet.cm()
            DialogMode.UPDATE -> title = Messages.Zmen_ucet.cm()
            DialogMode.DELETE -> title = Messages.Zrus_ucet.cm()
        }
    }

    override val root = form {

        fieldset {

            prefWidth = Options.fieldsetPrefWidth
            spacing = Options.fieldsetSpacing

            field(Messages.Synteticky_ucet.cm().withColon) {
                combobox<AccGroup>(accModel.group, Osnova.syntAccounts()) {
                    isDisable = mode != DialogMode.CREATE
                    validator {
                        if (it == null) error(Messages.Prazdna_skupina.cm())
                        else null
                    }
                }
            }

            field(Messages.Analytika.cm().withColon) {
                textfield(accModel.anal) {
                    isDisable = mode != DialogMode.CREATE
                    validator {
                        if (it == null || !Pattern.matches("\\d\\d\\d", it))
                            error(Messages.Analytika_musi_byt_tri_cislice.cm())
                        else null
                    }
                }
            }
            field(Messages.Nazev.cm().withColon) {
                textfield(accModel.name) {
                    isDisable = mode == DialogMode.DELETE
                }
            }
            if (acc?.balanced ?: true)
                field(Messages.Pocatecni_stav.cm().withColon) {
                    textfield(accModel.initAmount, AmountConverter) {
                        isDisable = mode == DialogMode.DELETE
                        validator {
                            if (it?.isLong() ?: false) null else error(Messages.Neplatna_castka.cm())
                        }
                    }

                }
        }
        buttonbar {
            button(Messages.Potvrd.cm()) {
                enableWhen(accModel.valid)
                action {
                    runAsync {
                        ok()
                    } fail {
                        accError(it)
                    } ui {
                        PaneTabs.refreshAccountPane()
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
    abstract val ok: () -> Unit
}

