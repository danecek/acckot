package acc.richclient.dialogs

import acc.business.Facade
import acc.model.AccGroup
import acc.model.AnalAcc
import acc.model.Osnova
import acc.richclient.panes.AccountPane
import acc.util.Messages
import javafx.scene.control.TableView
import tornadofx.*

class AccountDialogModel : ItemViewModel<AnalAcc>() {
    val group = bind(AnalAcc::parent)
    val anal = bind(AnalAcc::anal)
    val name = bind(AnalAcc::name)
}

class AccountDialog : Fragment() {

    val mode: DialogMode by params
    val model = AccountDialogModel()
    val acc: AnalAcc by params

    init {
        if (params["acc"] != null)
            model.item = params["acc"] as AnalAcc
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
                    when (mode) {
                        DialogMode.CREATE ->
                            Facade.createAccount(model.group.value, model.anal.value, model.name.value
                                    ?: "")
                          // DialogMode.UPDATE -> Facade.updateAccount(group.value, anal.value, number.value)
                        DialogMode.DELETE -> Facade.deleteAccount(model.item.id)

                    }
                    find<AccountPane>().refresh()
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

fun AccountPane.refresh() {
    val tw = root.content as TableView<AnalAcc>
    tw.items.setAll(Facade.allAccounts.observable())
}



