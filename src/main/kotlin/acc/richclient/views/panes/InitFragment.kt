package acc.richclient.views.panes

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.Init
import acc.richclient.views.dialogs.InitDeleteDialog
import acc.util.Messages
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import acc.richclient.views.PaneTabs

class InitFragment : Fragment() {

    val acc = params["acc"] as? AnalAcc

    val tw = tableview(Facade.getInits(acc).observable()) {
        readonlyColumn(Messages.Id.cm(), Init::id)

        readonlyColumn(Messages.Castka.cm(), Init::amount)
        nestedColumn(Messages.Ma_dati.cm()) {
            column<Init, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.maDati.number.toString())
            }
            column<Init, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.maDati.fullName)
            }
        }
        nestedColumn(Messages.Dal.cm()) {
            column<Init, String>(Messages.Cislo.cm()) { t ->
                SimpleStringProperty(t.value.dal.number)
            }
            column<Init, String>(Messages.Jmeno.cm()) { t ->
                SimpleStringProperty(t.value.dal.fullName)
            }
        }
        contextmenu {
            item(Messages.Zrus_inicializaci.cm()) {
                action {
                   find<InitDeleteDialog>(
                           params= mapOf("init" to selectedItem)).openModal()
                    PaneTabs.refreshInitPanes()
                }
            }
        }

    }
    override val root = InitPane(tw, acc)

}