package acc.richclient.dialogs.docs

import acc.Options
import acc.business.Facade
import acc.model.DocType
import acc.model.Document
import acc.richclient.controller.openTransactionCreateDialog
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.DocumentsView
import acc.richclient.panes.TransactionsView
import acc.util.DayMonthConverter
import acc.util.Messages
import acc.util.accError
import acc.util.withColon
import tornadofx.*
import java.time.LocalDate


abstract class DocumentDialogFragment(private val mode: DialogMode) : Fragment() {

    class DocumentDialogModel(doc: Document?) : ItemViewModel<Document>(doc) {
        val type = bind(Document::type)
        val number = bind(Document::number)
        val date = bind(Document::date)
        val description = bind(Document::description)
    }

    val doc = params["doc"] as? Document
    val docModel = DocumentDialogModel(doc)

    init {
        when (mode) {
            DialogMode.CREATE -> {
                title = Messages.Vytvor_doklad.cm()
                docModel.type.value = params["docType"] as DocType
                docModel.number.value = Facade.genDocumentNumber(docModel.type.value)
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
                label(docModel.type.value.abbr+docModel.number.value)
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
                }.isDisable = mode == DialogMode.DELETE
            }
        }
        buttonbar {
            button(acc.util.Messages.Potvrd.cm()) {
                enableWhen(docModel.valid)
                action {
                    runAsync {
                        ok()
                    } fail {
                        accError(it)
                    } ui {
                        find<DocumentsView>().update()
                        close()
                    }
                }
            }
            if (mode == DialogMode.CREATE)
                button(acc.util.Messages.Potvrd_a_zauctuj.cm()) {
                    enableWhen(docModel.valid)
                    action {
                        runAsync {
                           Facade.createDocument(docModel.type.value,
                                    docModel.number.value,
                                    docModel.date.value,
                                    docModel.description.value ?: "")

                        } fail {
                            accError(it)
                        } ui {
                            find<TransactionsView>().update()
                            openTransactionCreateDialog(it)
                        }
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



