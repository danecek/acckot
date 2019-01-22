package acc.richclient.views.dialogs

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.Document
import acc.model.DocumentType
import acc.model.Transaction
import acc.richclient.views.AmountConverter
import acc.richclient.views.PaneTabs
import acc.util.Messages
import tornadofx.*


class TransactionDialogModel(t: Transaction?) : ItemViewModel<Transaction>(t) {
    val id = bind(Transaction::id)
    val amount = bind(Transaction::amount)
    val maDati = bind(Transaction::maDati)
    val dal = bind(Transaction::dal)
    val document = bind(Transaction::document)
    val relatedDocument = bind(Transaction::relatedDocument)
}

abstract class TransactionAbstractDialog(mode: DialogMode) : Fragment() {

    val tr = params["tr"] as Transaction?
    val transModel = TransactionDialogModel(tr)
    val doc = params["doc"] as? Document
    val dal = params["dal"] as? AnalAcc

    init {
        title = when (mode) {
            DialogMode.CREATE -> {
                if (doc != null)
                    when (doc.type) {
                        DocumentType.BANK_STATEMENT -> Messages.Polozka_vypisu.cm()
                        DocumentType.INVOICE -> Messages.Faktura.cm()
                        DocumentType.INCOME -> Messages.Prijmovy_doklad.cm()
                        DocumentType.OUTCOME -> Messages.Vydajovy_doklad.cm()
                        DocumentType.EVENT -> Messages.Ucetni_udalost.cm()
                    }
                else
                    Messages.Vytvor_transakci.cm()
            }
            DialogMode.UPDATE -> Messages.Zmen_transakci.cm()
            DialogMode.DELETE -> Messages.Zrus_transakci.cm()
        }
        transModel.document.value = doc
        transModel.dal.value = dal
    }

    private val isBankStat
        get() = doc?.type == DocumentType.BANK_STATEMENT

    private fun maDati(): List<AnalAcc> {
        val accs = when (doc?.type) {
            DocumentType.BANK_STATEMENT -> Facade.allAccounts
            DocumentType.INVOICE -> Facade.allAccounts
            DocumentType.INCOME -> Facade.pokladna // 211
            DocumentType.OUTCOME -> Facade.allAccounts
            DocumentType.EVENT -> Facade.allAccounts //
            null -> Facade.allAccounts
        }
        if (accs.size == 1)
            transModel.maDati.value = accs.first()
        return accs
    }

    private fun dal(): List<AnalAcc> {
        val accs = when (doc?.type) {
            DocumentType.BANK_STATEMENT -> Facade.allAccounts
            DocumentType.INVOICE -> Facade.dodavatele //321
            DocumentType.INCOME -> Facade.allAccounts
            DocumentType.OUTCOME -> Facade.pokladna // 211
            DocumentType.EVENT -> Facade.allAccounts //
            null -> Facade.allAccounts
        }
        if (accs.size == 1)
            transModel.dal.value = accs.first()
        return accs
    }


    override val root = form {
        fieldset {
            field(Messages.Castka.cm()) {
                textfield(transModel.amount, AmountConverter) {
                    isDisable = mode == DialogMode.DELETE
                    validator {
                        if (it?.isLong() ?: false) null else error(Messages.Neplatna_castka.cm())
                    }
                }

            }
            field(Messages.Ma_dati.cm()) {
                combobox<AnalAcc>(transModel.maDati, maDati()) {
                    validator {
                        if (it == null) error() else null
                    }
                }.isDisable = mode == DialogMode.DELETE
            }
            field(Messages.Dal.cm()) {
                combobox<AnalAcc>(transModel.dal, dal()) {
                    validator {
                        if (it == null) error() else null
                    }
                }.isDisable = mode == DialogMode.DELETE

            }
            field(Messages.Doklad.cm()) {

                combobox<Document>(transModel.document, Facade.allDocuments) {
                    validator {
                        if (it == null) error() else null
                    }
                }

            }.isDisable = mode == DialogMode.DELETE
            if (isBankStat)
                field(Messages.Nezaplacene_faktury.cm()) {
                    hbox {
                        spacing = 10.0
                        val cb =
                                combobox<Document>(transModel.relatedDocument, Facade.unpaidInvoices.observable())

                        button(Messages.Smaz.cm()) {
                            action { cb.value = null }
                        }
                    }

                }.isDisable = mode == DialogMode.DELETE

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
            if (isBankStat) {
                button(Messages.Potvrd_a_dalsi.cm()) {
                    enableWhen(transModel.valid)
                    action {
                        ok()
                        PaneTabs.refreshTransactionPanes()
                        close()
                        find<TransactionCreateDialog>(params =
                        mapOf("doc" to transModel.document.value,
                                "dal" to transModel.dal.value)).openModal()
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


