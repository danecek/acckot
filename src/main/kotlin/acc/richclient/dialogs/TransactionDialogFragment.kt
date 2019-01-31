package acc.richclient.dialogs

import acc.Options
import acc.business.Facade
import acc.model.AnalAcc
import acc.model.DocType
import acc.model.Document
import acc.model.Transaction
import acc.richclient.PaneTabs
import acc.util.Messages
import acc.util.accError
import acc.util.withColon
import tornadofx.*


abstract class TransactionDialogFragment(mode: DialogMode) : Fragment() {

    class TransactionDialogModel(t: Transaction?) : ItemViewModel<Transaction>(t) {
        val id = bind(Transaction::id)
        val amount = bind(Transaction::amount)
        val maDati = bind(Transaction::maDati)
        val dal = bind(Transaction::dal)
        val document = bind(Transaction::doc)
        val relatedDocument = bind(Transaction::relatedDoc)
    }

    val tr = params["tr"] as Transaction?
    val transModel = TransactionDialogModel(tr)
    val doc: Document? = params["doc"] as? Document
    val dal = params["dalAnal"] as? AnalAcc

    enum class TransType {
        COMMON_TRANSACTION, TRANSACTION_FOR_DOC, TRANSACTION_FOR_STATM,
    }

    val transType =
            if (doc == null) TransType.COMMON_TRANSACTION
            else if (doc.type == DocType.BANK_STATEMENT)
                TransType.TRANSACTION_FOR_STATM
            else TransType.TRANSACTION_FOR_DOC

    init {
        when (mode) {
            DialogMode.CREATE ->
                when (transType) {
                    TransType.COMMON_TRANSACTION -> {
                        title = Messages.Vytvor_transakci.cm()
                    }
                    TransType.TRANSACTION_FOR_DOC -> {
                        title = Messages.Zauctuj_doklad.cm()
                        transModel.document.value = doc
                    }
                    TransType.TRANSACTION_FOR_STATM -> {
                        title = Messages.Zauctuj_polozku_vypisu.cm()
                        transModel.document.value = doc
                        transModel.dal.value = dal
                    }
                }
            DialogMode.UPDATE -> title = Messages.Vytvor_transakci.cm()
            DialogMode.DELETE -> title = Messages.Zrus_transakci.cm()
        }

    }

    private fun maDati(): List<AnalAcc> {
        val accs = when (doc?.type) {
            DocType.INCOME -> Facade.pokladny // 211
            else -> Facade.allAccounts
        }
        if (accs.size == 1)
            transModel.maDati.value = accs.first()
        return accs
    }

    private fun dal(): List<AnalAcc> {
        val accs = when (doc?.type) {
            DocType.INVOICE -> Facade.dodavatele //321
            DocType.OUTCOME -> Facade.pokladny // 211
            else -> Facade.allAccounts
        }
        if (accs.size == 1)
            transModel.dal.value = accs.first()
        return accs
    }

    var f: Field? = null

    override val root = form {
        fieldset {
            spacing = Options.fieldsetSpacing
            prefWidth = Options.fieldsetPrefWidth + 200
            prefHeight = 350.0
            field(Messages.Doklad.cm().withColon) {
                if (transModel.document.value == null) {
                    val cb = combobox(transModel.document, Facade.allDocuments) {
                        prefHeight = 50.0
                        validator {
                            if (it == null) error() else null
                        }
                    }
                    cb.valueProperty().addListener { _ ->
                        f!!.isDisable = cb.value.type != DocType.BANK_STATEMENT
                    }
                } else
                    label(transModel.document.value.toString())

            }.isDisable = mode == DialogMode.DELETE
            field(Messages.Castka.cm().withColon) {
                textfield(transModel.amount, AmountConverter) {
                    prefHeight = 50.0
                    isDisable = mode == DialogMode.DELETE
                    validator {
                        if (it?.isLong() ?: false) null else error(Messages.Neplatna_castka.cm())
                    }
                }

            }
            field(Messages.Ma_dati.cm().withColon) {
                runAsync {
                    maDati()
                } fail {
                    accError(it)
                } ui {
                    combobox(transModel.maDati, it) {
                        prefHeight = 50.0
                        converter = AccountConverter
                        validator {
                            if (it == null) error() else null
                        }

                    }.isDisable = mode == DialogMode.DELETE
                }
            }
            field(Messages.Dal.cm().withColon) {
                runAsync {
                    dal()
                } fail {
                    error(it)
                } ui {
                    combobox(transModel.dal, it) {
                        prefHeight = 50.0
                        converter = AccountConverter
                        validator {
                            if (it == null) error() else null
                        }
                    }.isDisable = mode == DialogMode.DELETE

                }
            }

            f = field(Messages.Odpovidajici_nezaplacena_faktura.cm().withColon) {
                hbox {
                    spacing = 5.0
                    runAsync {
                        Facade.unpaidInvoices
                    } fail {
                        accError(it)
                    } ui {
                        val cb =
                                combobox(transModel.relatedDocument,
                                        it) {
                                    prefHeight = 50.0
                                }
                        button(Messages.Smaz.cm()) {
                            action { cb.value = null }
                        }
                    }

                }

            }
            f!!.isDisable = true
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
                        PaneTabs.refreshDocAndTransPane()
                    }
                    close()
                }

            }
            if (transType == TransType.TRANSACTION_FOR_STATM) {
                button(Messages.Potvrd_a_dalsi.cm()) {
                    prefHeight = 50.0
                    enableWhen(transModel.valid)
                    action {
                        runAsync {
                            ok()
                        }.fail {
                            accError(it)
                        }.ui {
                            PaneTabs.refreshDocAndTransPane()
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


