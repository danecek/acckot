package acc.richclient.dialogs.accounts

import acc.model.AccGroup
import acc.model.AnalAcc
import acc.model.Osnova
import acc.richclient.dialogs.AmountConverter
import acc.richclient.dialogs.DialogMode
import acc.richclient.dialogs.DialogMode.*
import acc.richclient.panes.AccountsView
import acc.util.Messages
import acc.util.accError
import acc.util.withColon
import javafx.scene.control.ToggleGroup
import tornadofx.*
import java.util.regex.Pattern

class AccountDialogModel(acc: AnalAcc?) : ItemViewModel<AnalAcc>(acc) {
    val group = bind(AnalAcc::syntAccount)
    val anal = bind(AnalAcc::anal)
    val name = bind(AnalAcc::name)
    val initAmount = bind(AnalAcc::initAmount)
}

abstract class AccountDialogFragment(private val mode: DialogMode) : Fragment() {

    val acc: AnalAcc? = params[AnalAcc::class.simpleName] as? AnalAcc
    val accModel = AccountDialogModel(acc)

    init {
        title = when (mode) {
            CREATE -> Messages.Vytvor_ucet.cm()
            UPDATE -> Messages.Zmen_ucet.cm()
            DELETE -> Messages.Zrus_ucet.cm()
        }
    }

    override val root = form {

        fieldset {
            field(Messages.Synteticky_ucet.cm().withColon) {
                vbox {
                    spacing = 20.0
                    val cb = combobox<AccGroup>(accModel.group, Osnova.mainSyntAccounts) {
                        isDisable = mode != DialogMode.CREATE
                        validator {
                            if (it == null) error(Messages.Prazdna_skupina.cm())
                            else null
                        }
                    }
                    if (mode == CREATE)
                        hbox {
                            spacing = 20.0
                            val tg = ToggleGroup()
                            radiobutton(Messages.Hlavni.cm(), tg) {
                                isSelected = true
                                action {
                                    cb.items = Osnova.mainSyntAccounts.observable()
                                }

                            }
                            radiobutton(Messages.Vsechny.cm(), tg) {
                                action {
                                    cb.items = Osnova.syntAccounts.observable()
                                }

                            }
                        }
                }
            }
            field(Messages.Analytika.cm().withColon) {
                textfield(accModel.anal) {
                    isDisable = mode != CREATE
                    validator {
                        if (it == null || !Pattern.matches("\\d\\d\\d", it))
                            error(Messages.Analytika_musi_byt_tri_cislice.cm())
                        else null
                    }
                }
            }
            field(Messages.Nazev.cm().withColon) {
                textfield(accModel.name) {
                    isDisable = mode == DELETE
                }
            }
            if (acc?.isBalanced != false)
                field(Messages.Pocatecni_stav.cm().withColon) {
                    textfield(accModel.initAmount, AmountConverter) {
                        isDisable = mode == DELETE
                        validator {
                            if (it?.isLong() == true) null else error(Messages.Neplatna_castka.cm())
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
                        find<AccountsView>().refresh()
                        // PaneTabs.refreshAccountPane()
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


