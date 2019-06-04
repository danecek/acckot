package acc.richclient.dialogs.trans

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.DocType
import acc.model.Document
import acc.model.Transaction
import acc.richclient.dialogs.AmountConverter
import acc.richclient.dialogs.DialogMode
import acc.richclient.panes.TransactionsView
import acc.util.Messages
import acc.util.accError
import acc.util.withColon
import javafx.scene.control.ComboBox
import tornadofx.*

//enum class TransType {
//    COMMON_TRANSACTION, TRANSACTION_FOR_DOC, TRANSACTION_FOR_STATM,
//}

abstract class TransactionDialogFragment(mode: DialogMode) : Fragment() {

    class TransactionDialogModel(t: Transaction?) : ItemViewModel<Transaction>(t) {
        val id = bind(Transaction::id)
        val amount = bind(Transaction::amount)
        val maDati = bind(Transaction::maDati)
        val dal = bind(Transaction::dal)
        val document = bind(Transaction::doc)
        val relatedDocument = bind(Transaction::relatedDoc)
    }

    private val tr = params["tr"] as Transaction?
    val doc = if (tr == null)
        params["doc"]!! as Document  // trasakce se vytvari pro dokument
    else tr.doc
    val transModel = TransactionDialogModel(tr)
    val dal = params["dalAnal"] as? AnalAcc


    init {
        when (mode) {
            DialogMode.CREATE ->
                if (doc.type == DocType.BANK_STATEMENT) {
                    title = Messages.Zauctuj_polozku_vypisu.cm()
                    transModel.document.value = doc
                    transModel.dal.value = dal
                } else {
                    title = Messages.Zauctuj_doklad.cm().withColon.plus(doc.name)
                    transModel.document.value = doc
                }
            DialogMode.UPDATE -> {
                title = Messages.Zmen_transakci.cm()
            }

            DialogMode.DELETE -> {
                title = Messages.Zrus_transakci.cm()
            }
        }
    }

    private fun maDati(): List<AnalAcc> {
        val accs = when (doc.type) {
            DocType.INCOME -> Facade.pokladny // 211
            else -> Facade.allAccounts
        }
        if (accs.size == 1)
            transModel.maDati.value = accs.first()
        return accs
    }

    private fun dal(): List<AnalAcc> {
        val accs = when (doc.type) {
            DocType.INVOICE -> Facade.dodavatele //321
            DocType.OUTCOME -> Facade.pokladny // 211
            else -> Facade.allAccounts
        }
        if (accs.size == 1) // workaround
            transModel.dal.value = accs.first()
        return accs
    }

    lateinit var unpaidCB: ComboBox<Document>
    //lateinit var unpaidField: Field

    override val root = form {
        //prefWidth = 600.0
        fieldset {
            prefWidth = 600.0
            prefHeight = 350.0
            field(Messages.Castka.cm().withColon) {
                textfield(transModel.amount, AmountConverter) {
                    prefHeight = 50.0
                    isDisable = mode == DialogMode.DELETE
                    validator {
                        if (it?.isLong() == true) null else error(Messages.Neplatna_castka.cm())
                    }
                }

            }
            field(Messages.Ma_dati.cm().withColon) {
                isDisable = mode == DialogMode.DELETE
                runAsync {
                    maDati()
                } fail {
                    accError(it)
                } ui {
                    if (it.isEmpty()) {
                        close()
                        accError(Messages.Neexistuje_vhodny_analyticky_ucet.cm())
                    } else
                        combobox(transModel.maDati, it) {
                            prefHeight = 50.0
                            //  converter = AnalAccConverter
                            validator {
                                if (it == null) error() else null
                            }

                        }

                }
            }
            field(Messages.Dal.cm().withColon) {
                isDisable = mode == DialogMode.DELETE
                runAsync {
                    dal()
                } fail {
                    accError(it)
                } ui {
                    if (it.isEmpty()) {
                        close()
                        accError(Messages.Neexistuje_vhodny_analyticky_ucet.cm())
                    } else
                        combobox(transModel.dal, it) {
                            prefHeight = 50.0
                            validator {
                                if (it == null) error() else null
                            }
                        }

                }
            }
            field(Messages.Doklad.cm().withColon) {
                label(doc.toString())
/*                isDisable = mode == DialogMode.DELETE || transType == TransType.TRANSACTION_FOR_DOC
                val ad = Facade.allDocuments
                combobox(transModel.document, ad) {
                    prefHeight = 50.0
                    validator {
                        if (it == null) error() else null
                    }
                }.valueProperty().addListener { _, _, newValue ->
                    if (newValue.type != DocType.BANK_STATEMENT) {
                        unpaidCB.value = null
                        unpaidField.isDisable = true
                    } else
                        unpaidField.isDisable = false
                }*/
            }
            //   unpaidField =
            if (doc.type == DocType.BANK_STATEMENT)
                field(Messages.Zaplacena_faktura.cm().withColon) {
                    isDisable = mode == DialogMode.DELETE
                    hbox {
                        prefWidth = 100.0
                        spacing = 5.0
                        unpaidCB = combobox(transModel.relatedDocument) {
                            prefHeight = 50.0
                        }
                        button(Messages.Smaz.cm()) {
                            action { unpaidCB.value = null }
                        }
                        runAsync {
                            Facade.unpaidInvoices
                        } fail {
                            accError(it)
                        } ui {
                            unpaidCB.items.setAll(it)
                        }

                    }

                }
        }

        buttonbar {
            button(Messages.Potvrd.cm()) {
                padding = insets(10)
                enableWhen(transModel.valid)
                action {
                    runAsync {
                        ok()
                    }.fail {
                        accError(it)
                    }.ui {
                        find<TransactionsView>().update()
                    }
                    close()
                }

            }
            if (doc.type == DocType.BANK_STATEMENT) {
                button(Messages.Potvrd_a_dalsi_polozka.cm()) {
                    prefHeight = 50.0
                    enableWhen(transModel.valid)
                    action {
                        runAsync {
                            ok()
                        }.fail {
                            accError(it)
                        }.ui {
                            find<TransactionsView>().update()
                            find<TransactionCreateDialog>(params =
                            mapOf("doc" to transModel.document.value,
                                    "dalAnal" to transModel.dal.value)).openModal()
                        }
                        close()
                    }

                }
            }
            button(Messages.Zrus.cm()) {
                prefHeight = 50.0
                action {
                    close()
                }
            }
        }

    }

    abstract val ok: () -> Unit

}


