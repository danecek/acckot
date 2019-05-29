package acc.richclient.dialogs.docs

import acc.model.DocFilter
import acc.model.DocType
import acc.richclient.panes.DocumentsView
import acc.util.DayMonthConverter
import acc.util.Messages
import acc.util.withColon
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*


class DocumentsShowDialog : Fragment() {

    class DocFilterModel : ItemViewModel<DocFilter>() {
        val from = bind(DocFilter::from)
        val tto = bind(DocFilter::tto)
    }

    private val a: Array<SimpleBooleanProperty> =
            Array(DocType.values().size, init = { SimpleBooleanProperty(false) })

    val docFilterModel = DocFilterModel()
    fun setAll() {
        a.forEach {
            it.value = true
        }
    }

    override val root =
            form {
                title = Messages.Filter_dokladu.cm()
                fieldset {
                    DocType.values().forEach {
                        checkbox(it.text, a[it.ordinal])
                    }
                    button(Messages.Vsechny_typy.cm()) {
                        action { setAll() }
                    }

                    field(Messages.Od.cm().withColon) {
                        datepicker(docFilterModel.from) {
                            prefHeight = 50.0
                            converter = DayMonthConverter
                        }
                    }
                    field(Messages.Do.cm().withColon) {
                        datepicker(docFilterModel.tto) {
                            prefHeight = 50.0
                            converter = DayMonthConverter
                        }
                    }
                }
                buttonbar {
                    button(Messages.Potvrd.cm()) {
                        enableWhen(docFilterModel.valid)
                        action {
                            val ms = mutableSetOf<String>()
                            DocType.values().forEach {
                                if (a[it.ordinal].value)
                                    ms.add(it.abbr)
                            }

                            with (find<DocumentsView>()){
                                docFilter = DocFilter(ms,
                                        docFilterModel.from.value,
                                        docFilterModel.tto.value)
                                update()
                            }
                       //     PaneTabs.showDocumentPane(docFilter)
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