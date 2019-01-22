package acc.richclient.views.dialogs

import acc.business.Facade
import acc.business.MadatiDal
import acc.model.AnalAcc
import acc.model.Init
import acc.richclient.views.AmountConverter
import acc.richclient.views.PaneTabs
import acc.util.Messages
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.ToggleGroup
import tornadofx.*

class InitDialogModel(t: Init?) : ItemViewModel<Init>(t) {
    val id = bind(Init::id)
    val amount = bind(Init::amount)
    val maDati = bind(Init::maDati)
    val madatiDal = SimpleObjectProperty<MadatiDal>()
}

abstract class InitAbstractDialog(mode: DialogMode) : Fragment() {

    private val init: Init? = params["init"] as? Init
    val model = InitDialogModel(init)

    init {
        title = when (mode) {
            DialogMode.CREATE -> Messages.Vytvor_inicializaci.cm()
            //  DialogMode.UPDATE -> Messages.Zmen_inicializaci.cm()
            DialogMode.DELETE -> Messages.Zrus_inicializaci.cm()
            else -> throw UnsupportedOperationException()
        }

    }

    override val root = form {
        fieldset {
            field(Messages.Castka.cm()) {
                textfield(model.amount, AmountConverter) {
                    isDisable = mode == DialogMode.DELETE
                    validator {
                        if (it?.isLong() ?: false) null
                        else error(Messages.Neplatna_castka.cm())
                    }
                }

            }
            val tg = ToggleGroup()
            radiobutton(Messages.Ma_dati.cm(), tg) {
                action { model.madatiDal.value = MadatiDal.MA_DATI }
            }.isSelected = true
            radiobutton(Messages.Dal.cm(), tg) {
                action { model.madatiDal.value = MadatiDal.DAL }
            }
            field(Messages.Ucet.cm()) {
                combobox<AnalAcc>(model.maDati, Facade.allAccounts.observable()) {
                    validator {
                        if (it == null) error() else null
                    }
                }
            }
        }

        buttonbar {
            button(Messages.Potvrd.cm()) {
                enableWhen(model.valid)
                action {
                    ok()
                    PaneTabs.refreshInitPanes()
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