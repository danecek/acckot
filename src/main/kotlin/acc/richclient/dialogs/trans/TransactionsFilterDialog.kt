package acc.richclient.dialogs.trans

import acc.business.Facade
import acc.model.TransactionFilter
import acc.richclient.panes.PaneTabs
import acc.richclient.panes.TransactionsView
import acc.util.DayMonthConverter
import acc.util.Messages
import acc.util.accFail
import acc.util.withColon
import tornadofx.*

class TransactionFilterModel : ItemViewModel<TransactionFilter>() {
    val from = bind(TransactionFilter::from)
    val tto = bind(TransactionFilter::tto)
    val acc = bind(TransactionFilter::acc)
    val doc = bind(TransactionFilter::doc)
}

class TransactionsFilterDialog : Fragment() {

    private val transFilterModel = TransactionFilterModel()

    override val root =
            form {
                prefWidth = 500.0
                title = Messages.Filter_transakci.cm()
                fieldset {
                    spacing = 10.0
                    field(Messages.S_uctem.cm().withColon) {
                        tornadofx.runAsync {
                            Facade.allAccounts
                        } fail {
                            accFail(it)
                        } ui {
                            combobox(transFilterModel.acc, it)
                        }
                    }
/*                    field(Messages.Doklad.cm().withColon) {
                        tornadofx.runAsync {
                            Facade.allDocuments
                        } fail {
                            accFail(it)
                        } ui {
                            combobox(transFilterModel.doc, it) {
                                converter = DocumentConverter
                            }
                        }
                    }*/
                    field(Messages.Od.cm().withColon) {
                        datepicker(transFilterModel.from) {
                            prefHeight = 50.0
                            converter = DayMonthConverter
                        }
                    }
                    field(Messages.Do.cm().withColon) {
                        datepicker(transFilterModel.tto) {
                            prefHeight = 50.0
                            converter = DayMonthConverter
                        }
                    }
                    buttonbar {
                        padding = insets(20)
                        button(Messages.Potvrd.cm()) {
                            enableWhen(transFilterModel.valid)
                            action {
                                tornadofx.find<TransactionsView>().transFilter =
                                        TransactionFilter(transFilterModel.from.value, transFilterModel.tto.value,
                                                transFilterModel.acc.value, transFilterModel.doc.value)
                                PaneTabs.selectView(TransactionsView::class)
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
}
