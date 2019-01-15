package acc.richclient.dialogs

import acc.business.Facade
import acc.model.Document
import acc.model.DocumentType
import acc.richclient.panes.DocumentPane
import acc.util.Global
import acc.util.Messages
import javafx.scene.control.TableView
import javafx.util.StringConverter
import tornadofx.*
import java.time.LocalDate

class DocumentDialogModel : ItemViewModel<Document>() {
    val type = bind(Document::type)
    val date = bind(Document::date)
    val number = bind(Document::number)
    val description = bind(Document::description)
}

class DocumentDialog : Fragment() {

    private val mode: DialogMode by params
    val model = DocumentDialogModel()// by inject()
    private val doc: Document by params

    init {
        if (mode != DialogMode.CREATE && params["doc"] != null)
            model.item = params["doc"] as Document
        when (mode) {
            DialogMode.CREATE -> title = Messages.Vytvor_doklad.cm()
            DialogMode.UPDATE -> title = Messages.Zmen_doklad.cm()
            DialogMode.DELETE -> title = Messages.Zrus_doklad.cm()
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
                    when (mode) {
                        DialogMode.CREATE ->
                            Facade.createDocument(model.type.value, model.number.value,
                                    model.date.value, model.description.value ?: "")
                        DialogMode.UPDATE ->
                            Facade.updateDocument(doc.id, model.date.value, model.description.value)
                        DialogMode.DELETE -> Facade.deleteDocument(doc.id)

                    }
                    find<DocumentPane>().refresh()
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
}

fun DocumentPane.refresh() {
    val tw = root.content as TableView<Document>
    tw.items.setAll(Facade.allDocuments.observable())
}

