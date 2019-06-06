package acc.richclient.dialogs.docs

import acc.model.DocFilter
import acc.model.DocType
import acc.richclient.panes.DocumentsView
import acc.richclient.panes.PaneTabs
import acc.util.*
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*


class DocumentsFilterDialog : Fragment() {

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
                prefWidth = 500.0
                spacing = 10.0
                title = Messages.Nastav_filter_dokladu.cm()
                fieldset {
                    spacing = 10.0
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
                            val ms = mutableSetOf<DocType>()
                            DocType.values().forEach {
                                if (a[it.ordinal].value)
                                    ms.add(it)
                            }
                            if (ms.isEmpty())
                                Messages.Alespon_jeden_typ_musi_byt_vybran.fxAlert()
                            else {
                                with(find<DocumentsView>()) {
                                    docFilter = DocFilter(ms,
                                            docFilterModel.from.value,
                                            docFilterModel.tto.value)
                                    update()
                                }
                                PaneTabs.selectView<DocumentsView>()
                                close()
                            }
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
