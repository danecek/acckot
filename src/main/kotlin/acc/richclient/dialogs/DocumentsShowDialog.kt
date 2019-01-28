package acc.richclient.dialogs

import acc.model.DocFilter
import acc.model.DocType
import acc.util.DayMonthConverter
import acc.richclient.PaneTabs
import acc.richclient.panes.DocumentFragment
import acc.Options
import acc.util.Messages
import acc.util.withColon
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*


class DocumentsShowDialog : Fragment() {

    class DocFilterModel : ItemViewModel<DocFilter>() {
        val types = bind(DocFilter::types)
        val from = bind(DocFilter::from)
        val tto = bind(DocFilter::tto)
    }

    val a: Array<SimpleBooleanProperty> =
            Array(DocType.values().size, init = { SimpleBooleanProperty(false) })

    val docFilterModel = DocFilterModel()
    fun setAll() {
        a.forEach {
            it.value = true
        }
    }

    override val root =
            form {
                title = Messages.Zobraz_doklady.cm()
                fieldset {
                    spacing = Options.fieldsetSpacing
                    prefWidth = Options.fieldsetPrefWidth

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
                            val df = DocFilter(ms,
                                    docFilterModel.from.value,
                                    docFilterModel.tto.value)
                            PaneTabs.addTab(Messages.Doklady.cm(),
                                    find<DocumentFragment>(mapOf(df::class.simpleName to df)).root)
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
