package acc.richclient.dialogs

import acc.Options
import acc.business.Facade
import acc.model.AnalAcc
import acc.model.DocType
import acc.model.Document
import acc.model.Transaction
import acc.richclient.PaneTabs
import acc.util.Messages
import acc.util.withColon
import tornadofx.*


abstract class TransactionDialogFragment(mode: DialogMode) : Fragment() {

    class TransactionDialogModel(t: Transaction?) : ItemViewModel<Transaction>(t) {
        val id = bind(Transaction::id)
        val amount = bind(Transaction::amount)
        val maDati = bind(Transaction::maDati)
        val dal = bind(Transaction::dal)
        val document = bind(Transaction::doc)
        val relatedDocument = bind(Transaction::relatedDocId)
    }

    val tr = params["tr"] as Transaction?
    val transModel = TransactionDialogModel(tr)
    val doc: Document? = params["doc"] as? Document
    val dal = params["dalAnal"] as? AnalAcc

    enum class TransType {
        GENERAL_TRAN, DOC_TRANS, STAT_ITEM,
    }

    val transType =
            if (doc == null) TransType.GENERAL_TRAN
            else if (doc.type == DocType.BANK_STATEMENT)
                TransType.STAT_ITEM
            else TransType.DOC_TRANS

    init {
        if (mode == DialogMode.CREATE)
            when (transType) {
                TransType.GENERAL_TRAN -> {
                    title = Messages.Vytvor_transakci.cm()
                }
                TransType.DOC_TRANS -> {
                    title = Messages.Zauctuj_doklad.cm()
                    transModel.document.value = doc
                }
                TransType.STAT_ITEM -> {
                    title = Messages.Zauctuj_polozku_vypisu.cm()
                    transModel.document.value = doc
                    transModel.dal.value = dal
                }
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


    override val root = form {
        fieldset {
            spacing = Options.fieldsetSpacing
            prefWidth = Options.fieldsetPrefWidth
            field(Messages.Doklad.cm().withColon) {
                if (transModel.document.value == null)
                    combobox(transModel.document, Facade.allDocuments) {
                        validator {
                            if (it == null) error() else null
                        }
                    } else
                    label(transModel.document.value.toString())

            }.isDisable = mode == DialogMode.DELETE
            field(Messages.Castka.cm().withColon) {
                textfield(transModel.amount, AmountConverter) {
                    isDisable = mode == DialogMode.DELETE
                    validator {
                        if (it?.isLong() ?: false) null else error(Messages.Neplatna_castka.cm())
                    }
                }

            }
            field(Messages.Ma_dati.cm().withColon) {
                combobox(transModel.maDati, maDati()) {
                    converter = AccountConverter
                    validator {
                        if (it == null) error() else null
                    }
                }.isDisable = mode == DialogMode.DELETE
            }
            field(Messages.Dal.cm().withColon) {
                combobox(transModel.dal, dal()) {
                    converter = AccountConverter
                    validator {
                        if (it == null) error() else null
                    }
                }.isDisable = mode == DialogMode.DELETE

            }
            if (transType == TransType.GENERAL_TRAN)
                field(Messages.Souvisejici_doklad.cm().withColon) {
                    hbox {
                        spacing = 5.0
                        val cb =
                                combobox(transModel.relatedDocument,
                                        Facade.allDocuments)

                        button(Messages.Smaz.cm()) {
                            action { cb.value = null }
                        }
                    }

                }.isDisable = mode == DialogMode.DELETE
            else if (transType == TransType.STAT_ITEM) {
                field(Messages.Odpovidajici_faktura.cm().withColon) {
                    hbox {
                        spacing = 5.0
                        val cb =
                                combobox(transModel.relatedDocument,
                                        Facade.unpaidInvoices)
                        button(Messages.Smaz.cm()) {
                            action { cb.value = null }
                        }
                    }

                }.isDisable = mode == DialogMode.DELETE
            }

        }
        buttonbar {
            button(Messages.Potvrd.cm()) {
                enableWhen(transModel.valid)
                action {
                    ok()
                    PaneTabs.refreshTransactionPanes()
                    close()
                }

            }
            if (transType == TransType.STAT_ITEM) {
                button(Messages.Potvrd_a_dalsi.cm()) {
                    enableWhen(transModel.valid)
                    action {
                        ok()
                        PaneTabs.refreshTransactionPanes()
                        close()
                        find<TransactionCreateDialog>(params =
                        mapOf("doc" to transModel.document.value,
                                "dalAnal" to transModel.dal.value)).openModal()
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

    abstract val ok: () -> Unit

}


