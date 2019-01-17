/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.dialogs

import acc.business.Facade
import acc.model.TransactionFilter
import acc.richclient.panes.TransactionsFragment
import acc.richclient.views.PaneTabs
import acc.util.Messages
import tornadofx.*

class TransactionFilterModel : ItemViewModel<TransactionFilter>() {
    val oD = bind(TransactionFilter::Od)
    val Do = bind(TransactionFilter::Do)
    val doc = bind(TransactionFilter::doc)
    val relatedDoc = bind(TransactionFilter::relatedDoc)
}

class TransactionsShowDialog : Fragment() {

    val model = TransactionFilterModel()

    override val root =
            form {
                title = Messages.Zobraz_transakce.cm()
                fieldset {
                    field(Messages.Od.cm() + AbstractDialog.DEL) {
                        datepicker(model.oD)
                    }
                    field(Messages.Do.cm() + AbstractDialog.DEL) {
                        datepicker(model.Do)
                    }
                    field(Messages.Doklad.cm() + AbstractDialog.DEL) {
                        combobox(model.doc, Facade.allDocuments)
                    }
                    field(Messages.Pridruzeny_doklad.cm() + AbstractDialog.DEL) {
                        combobox(model.doc, Facade.allDocuments)
                    }

                }
                buttonbar {
                    button(Messages.Potvrd.cm()) {
                        enableWhen(model.valid)
                        action {
                            model.commit()
                            val tf = TransactionFilter(model.oD.value, model.Do.value,
                                    model.doc.value, model.relatedDoc.value)
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
