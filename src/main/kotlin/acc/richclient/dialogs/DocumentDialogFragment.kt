package acc.richclient.dialogs

import acc.Options
import acc.business.Facade
import acc.model.DocId
import acc.model.DocType
import acc.model.Document
import acc.richclient.PaneTabs
import acc.richclient.controller.openTransactionCreateDialog
import acc.util.DayMonthConverter
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

abstract class DocumentDialogFragment(private val mode: DialogMode) : Fragment() {

    val docModel = DocumentDialogModel(params["doc"] as? Document)

    init {
        when (mode) {
            DialogMode.CREATE -> {
                title = Messages.Vytvor_doklad.cm()
                docModel.type.value = params["docType"] as DocType
                docModel.number.value = Facade.genDocumentId(docModel.type.value)
                docModel.id.value = DocId(docModel.type.value, docModel.number.value)
                docModel.date.value = initDate()
            }
            DialogMode.UPDATE -> title = Messages.Zmen_doklad.cm()
            DialogMode.DELETE -> title = Messages.Zrus_doklad.cm()
        }
    }

    private fun initDate(): LocalDate {
        val now = LocalDate.now()
        return if (now.year == Options.year) now
        else LocalDate.of(Options.year, 1, 1)
    }


    override val root = form {
        fieldset {
            field(acc.util.Messages.Typ.cm().withColon) {
                label(docModel.type.value.text)
            }
            field(acc.util.Messages.Jmeno.cm().withColon) {
                label(docModel.id.value.toString())
            }
            field(acc.util.Messages.Datum.cm().withColon) {
                datepicker(docModel.date) {
                    prefHeight = 50.0
                    isEditable = false
                    isDisable = mode == DialogMode.DELETE
                    converter = DayMonthConverter
                    validator {
                        if (it!!.year != Options.year)
                            error(acc.util.Messages.Chybny_rok.cm())
                        else null
                    }
                }
            }
            field(acc.util.Messages.Popis.cm().withColon) {
                textarea(docModel.description) {
                    prefRowCount = 2
                }.isDisable = mode == acc.richclient.dialogs.DialogMode.DELETE
            }
        }
        buttonbar {
            button(acc.util.Messages.Potvrd.cm()) {
                enableWhen(docModel.valid)
                action {
                    runAsync {
                        ok()
                    } fail {
                        error(it)
                    } ui {
                        PaneTabs.refreshDocAndTransPane()
                        close()
                    }
                }

            }
            if (mode == DialogMode.CREATE)
                button(acc.util.Messages.Potvrd_a_zauctuj.cm()) {
                    enableWhen(docModel.valid)
                    action {
                        runAsync {
                            ok()
                            val docId = DocId(docModel.type.value, docModel.number.value)
                            Facade.documentById(docId)
                        } fail {
                            error(it)
                        } ui {
                            if (it!=null) {
                                PaneTabs.refreshDocAndTransPane()
                                openTransactionCreateDialog(it)
                            }
                            close()
                        }


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



