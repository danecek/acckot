package acc.richclient.dialogs

import acc.business.Facade
import acc.model.AnalAcc
import acc.model.Document
import acc.model.Transaction
import acc.richclient.views.PaneTabs
import acc.util.Messages
import javafx.util.StringConverter
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

    val tr:Transaction? by params
    val model = TransactionDialogModel(tr)
    val c by params

    init {
        when (mode) {
            DialogMode.CREATE -> title = Messages.Vytvor_transakci.cm()
            DialogMode.UPDATE -> title = Messages.Zmen_transakci.cm()
            DialogMode.DELETE -> title = Messages.Zrus_transakci.cm()
        }

    }

    override val root = form {
        fieldset {
            field(Messages.Castka.cm()) {
                val amount = textfield(model.amount,
                        object : StringConverter<Long>() {
                            override fun toString(obj: Long?): String {
                                return obj.toString()
                            }

                            override fun fromString(string: String?): Long {
                                try {
                                    return string?.toLong() ?: 0
                                } catch (ex: Exception) {
                                    return 0;
                                }

                            }

                        }
                )
                amount.isDisable = mode == DialogMode.DELETE
                amount.validator {
                    if (it?.isLong() ?: false) null else error(Messages.neplatna_castka.cm())
                }

            }
            field(Messages.Ma_dati.cm()) {
                combobox<AnalAcc>(model.maDati, Facade.allAccounts.observable()) {
                    validator {
                        if (it == null) error() else null
                    }
                }
            }
            field(Messages.Dal.cm()) {
                combobox<AnalAcc>(model.dal, Facade.allAccounts.observable()) {
                    validator {
                        if (it == null) error() else null
                    }
                }

            }
            field(Messages.Doklad.cm()) {
                combobox<Document>(model.document, Facade.allDocuments.observable()) {
                    validator {
                        if (it == null) error() else null
                    }
                }
            }
            field(Messages.Souvisejici_doklad.cm()) {
                combobox<Document>(model.document, Facade.allDocuments.observable())
            }

        }
        buttonbar {
            button(Messages.Potvrd.cm()) {
                enableWhen(model.valid)
                action {
                    ok()
                    PaneTabs.refreshTransactionPanes()
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

    abstract val ok: () -> Unit

}


