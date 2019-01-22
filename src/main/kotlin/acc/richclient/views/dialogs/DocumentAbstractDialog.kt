package acc.richclient.views.dialogs

import acc.business.Facade
import acc.model.Document
import acc.model.DocumentType
import acc.richclient.views.DateConverter
import acc.richclient.views.PaneTabs
import acc.util.Messages
import acc.util.withColon
import tornadofx.*
import java.time.LocalDate

class DocumentDialogModel(doc: Document?) : ItemViewModel<Document>(doc) {
    val id = bind(Document::id)
    val type = bind(Document::type)
    val date = bind(Document::date)
    val number = bind(Document::number)
    val description = bind(Document::description)
}

abstract class DocumentAbstractDialog(private val mode: DialogMode) : Fragment() {

    val docModel = DocumentDialogModel(params["doc"] as? Document)

    init {
        when (mode) {
            DialogMode.CREATE -> {
                title = Messages.Vytvor_doklad.cm()
                docModel.type.value = params["docType"] as? DocumentType
                docModel.number.value = Facade.genDocumentId(docModel.type.value)
            }
            DialogMode.UPDATE -> title = Messages.Zmen_doklad.cm()
            DialogMode.DELETE -> title = Messages.Zrus_doklad.cm()
        }
    }

    override val root = form {
        fieldset {
            field(acc.util.Messages.Typ_dokladu.cm().withColon) {
                label(docModel.type.value.text)
            }
            field(acc.util.Messages.Cislo.cm().withColon) {
                label(docModel.number.value.toString())
            }
            field(acc.util.Messages.Datum.cm().withColon) {
                datepicker(docModel.date) {
                    value = LocalDate.now()
                    isEditable = false
                    isDisable = mode == DialogMode.DELETE
                    converter = DateConverter
                    validator {
                        if (it == null || it.year != 2019) error(acc.util.Messages.Chybne_datum.cm())
                        else null
                    }
                }

            }
            field(acc.util.Messages.Popis.cm().withColon) {
                textfield(docModel.description).isDisable = mode == acc.richclient.views.dialogs.DialogMode.DELETE
            }
        }
        buttonbar {
            button(acc.util.Messages.Potvrd.cm()) {
                enableWhen(docModel.valid)
                action {
                    ok()
                    PaneTabs.refreshDocumentPane()
                    close()
                }

            }
            button(acc.util.Messages.Zrus.cm()) {
                action {
                    close()
                }
            }
        }

    }

    abstract val ok: () -> Unit
}



