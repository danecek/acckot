package acc.richclient.dialogs

import acc.model.Document
import acc.model.DocumentType
import acc.richclient.views.PaneTabs
import acc.util.Global
import acc.util.Messages
import javafx.util.StringConverter
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

   // private val doc: Document? by params
    val model = DocumentDialogModel(params["doc"] as? Document)

    init {
        title = when (mode) {
            DialogMode.CREATE -> Messages.Vytvor_doklad.cm()
            DialogMode.UPDATE -> Messages.Zmen_doklad.cm()
            DialogMode.DELETE -> Messages.Zrus_doklad.cm()
        }
    }

    override val root = form {
        fieldset {
            field(acc.util.Messages.Typ_dokladu.cm()) {
                val gcb = combobox<DocumentType>(model.type, DocumentType.values().toList())
                gcb.isDisable = mode == DialogMode.DELETE
                gcb.validator {
                    if (it == null) error(acc.util.Messages.neplatny_typ_dokumentu.cm()) else null
                }
            }

            field(acc.util.Messages.Datum.cm()) {
                val dp = datepicker(model.date)
                dp.value = LocalDate.now()
                dp.isEditable = false
                dp.isDisable = mode == acc.richclient.dialogs.DialogMode.DELETE
                dp.converter = object : StringConverter<LocalDate>() {
                    override fun toString(date: LocalDate?): String {
                        return if (date != null) {
                            Global.df.format(date)
                        } else {
                            ""
                        }
                    }

                    override fun fromString(string: String?): LocalDate? {
                        return if (string != null && !string.isEmpty()) {
                            LocalDate.parse(string, Global.df)
                        } else {
                            null
                        }
                    }
                }
                dp.validator {
                    if (it == null || it.year != 2019) error(acc.util.Messages.chybne_datum.cm()) else null
                }

            }
            field(acc.util.Messages.Cislo.cm()) {
                val numberTF = textfield(model.number)
                numberTF.isDisable = mode == acc.richclient.dialogs.DialogMode.DELETE
                numberTF.validator {
                    if (it.isNullOrBlank()) error(acc.util.Messages.Cislo.cm()) else null
                }
            }
            field(acc.util.Messages.Popis.cm()) {
                textfield(model.description).isDisable = mode == acc.richclient.dialogs.DialogMode.DELETE
            }
        }
        buttonbar {
            button(acc.util.Messages.Potvrd.cm()) {
                enableWhen(model.valid)
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



