/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.dialogs

import acc.business.Facade
import acc.model.TransactionFilter
import acc.util.DayMonthConverter
import acc.richclient.PaneTabs
import acc.richclient.panes.TransactionsFragment
import acc.Options
import acc.util.Messages
import acc.util.withColon
import tornadofx.*

class TransactionFilterModel : ItemViewModel<TransactionFilter>() {
    val from = bind(TransactionFilter::from)
    val tto = bind(TransactionFilter::tto)
    val acc = bind(TransactionFilter::acc)
    val doc = bind(TransactionFilter::doc)
}

class TransactionsShowDialog : Fragment() {


    val transFilterModel = TransactionFilterModel()

    override val root =
            form {
                title = Messages.Zobraz_transakce.cm()
                fieldset {
                    spacing = Options.fieldsetSpacing
                    prefWidth = Options.fieldsetPrefWidth
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
                    field(Messages.Ucet.cm().withColon) {
                        combobox(transFilterModel.acc, Facade.allAccounts){
                            converter = AccountConverter
                        }
                    }
                    field(Messages.Doklad.cm().withColon) {
                        combobox(transFilterModel.doc, Facade.allDocuments) {
                            converter = DocumentConverter
                        }
                    }

                }
                buttonbar {
                    button(Messages.Potvrd.cm()) {
                        enableWhen(transFilterModel.valid)
                        action {
                            val tf = TransactionFilter(transFilterModel.from.value, transFilterModel.tto.value,
                                    transFilterModel.acc.value, transFilterModel.doc.value)
                            PaneTabs.addTab(Messages.Transakce.cm(),
                                    find<TransactionsFragment>(mapOf("tf" to tf)).root)
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
