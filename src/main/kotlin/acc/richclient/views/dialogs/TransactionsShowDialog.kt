/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.views.dialogs

import acc.business.Facade
import acc.model.TransactionFilter
import acc.richclient.views.PaneTabs
import acc.richclient.views.panes.TransactionsFragment
import acc.richclient.views.DateConverter
import acc.richclient.views.DocumentConverter
import acc.util.Messages
import acc.util.withColon
import tornadofx.*

class TransactionFilterModel : ItemViewModel<TransactionFilter>() {
    val oD = bind(TransactionFilter::Od)
    val Do = bind(TransactionFilter::Do)
    val acc = bind(TransactionFilter::account)
    val doc = bind(TransactionFilter::doc)
}

class TransactionsShowDialog : Fragment() {


    val model = TransactionFilterModel()

    override val root =
            form {
                title = Messages.Zobraz_transakce.cm()
                fieldset {
                    field(Messages.Od.cm().withColon) {
                        datepicker(model.oD) {
                            converter = DateConverter
                        }
                    }
                    field(Messages.Do.cm().withColon) {
                        datepicker(model.Do) {
                            converter = DateConverter
                        }
                    }
                    field(Messages.Ucet.cm().withColon) {
                        combobox(model.acc, Facade.allAccounts)
                    }
                    field(Messages.Doklad.cm().withColon) {
                        combobox(model.doc, Facade.allDocuments) {
                            converter = DocumentConverter
                        }
                    }

                }
                buttonbar {
                    button(Messages.Potvrd.cm()) {
                        enableWhen(model.valid)
                        action {
                            val tf = TransactionFilter(model.oD.value, model.Do.value,
                                    model.acc.value,model.doc.value)
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
