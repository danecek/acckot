package acc.richclient.dialogs

import acc.business.Facade
import acc.business.MadatiDal
import acc.model.AnalAcc
import acc.model.Init
import acc.richclient.views.PaneTabs
import acc.util.Messages
import javafx.beans.property.*
import javafx.scene.control.ToggleGroup
import javafx.util.StringConverter
import tornadofx.*

class InitDialogModel(t: Init?) : ItemViewModel<Init>(t) {
    val id = bind(Init::id)
    val amount = bind(Init::amount)
    val maDati = bind(Init::maDati)
    val madatiDal = SimpleObjectProperty<MadatiDal>()
}

abstract class InitAbstractDialog(mode: DialogMode) : Fragment() {

    private val tr: Init? = params["init"] as? Init
    val model = InitDialogModel(tr)

    init {
        title = when (mode) {
            DialogMode.CREATE -> Messages.Vytvor_inicializaci.cm()
            DialogMode.UPDATE -> Messages.Zmen_inicializaci.cm()
            DialogMode.DELETE -> Messages.Zrus_inicializaci.cm()
        }

    }

    override val root = form {
        fieldset {
            field(Messages.Castka.cm()) {
                val amount = textfield(model.amount,
                        object : StringConverter<Long>() {
                            override fun toString(obj: Long?): String {
                                return obj.toString()
                            }

                            override fun fromString(string: String?): Long {
                                try {
                                    return string?.toLong() ?: 0
                                } catch (ex: Exception) {
                                    return 0;
                                }

                            }

                        }
                )
                amount.isDisable = mode == DialogMode.DELETE
                amount.validator {
                    if (it?.isLong() ?: false) null else error(Messages.neplatna_castka.cm())
                }

            }
            field("") {
                val tg = ToggleGroup()

                radiobutton(Messages.Ma_dati.cm(), tg, MadatiDal.MA_DATI) {
                    model.madatiDal.value = userData as MadatiDal
                }
                radiobutton(Messages.Dal.cm(), tg, MadatiDal.DAL){
                    model.madatiDal.value = userData as MadatiDal
                }
            }

        }
        field(Messages.Ucet.cm()) {
            combobox<AnalAcc>(model.maDati, Facade.allAccounts.observable()) {
                validator {
                    if (it == null) error() else null
                }
            }
        }

        buttonbar  {
            button(Messages.Potvrd.cm()) {
                enableWhen(model.valid)
                action {
                    ok()
                    PaneTabs.refreshTransactionPanes()
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