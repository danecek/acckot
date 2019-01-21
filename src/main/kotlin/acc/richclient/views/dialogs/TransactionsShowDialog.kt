/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.views.dialogs

import acc.business.Facade
import acc.model.Document
import acc.model.TransactionFilter
import acc.richclient.views.PaneTabs
import acc.richclient.views.panes.TransactionsFragment
import acc.richclient.views.DateConverter
import acc.util.Messages
import acc.util.withColon
import javafx.util.StringConverter
import tornadofx.*

class TransactionFilterModel : ItemViewModel<TransactionFilter>() {
    val oD = bind(TransactionFilter::Od)
    val Do = bind(TransactionFilter::Do)
    val docId = bind(TransactionFilter::doc)
    val relatedDoc = bind(TransactionFilter::relatedDoc)
}

class TransactionsShowDialog : Fragment() {


    object DocumentConverter : StringConverter<Document>() {
        override fun fromString(string: String?): Document =
                throw UnsupportedOperationException()

        override fun toString(doc: Document?): String =
                if (doc == null) ""
                else doc.number.toString().withColon + doc.description.take(50)

    }

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
                    field(Messages.Doklad.cm().withColon) {
                        combobox(model.docId, Facade.allDocuments) {
                            converter = DocumentConverter
                        }
                    }
                    field(Messages.Pridruzeny_doklad.cm().withColon) {
                        combobox(model.relatedDoc, Facade.allDocuments) {
                            converter = DocumentConverter
                        }
                    }

                }
                buttonbar {
                    button(Messages.Potvrd.cm()) {
                        enableWhen(model.valid)
                        action {
                            //    docModel.commit()
                            val tf = TransactionFilter(model.oD.value, model.Do.value,
                                    model.docId.value, model.relatedDoc.value)
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
