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
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

/*SEVERE: Uncaught error
java.lang.IndexOutOfBoundsException
at com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList.subList(ReadOnlyUnbackedObservableList.java:136)
at javafx.collections.ListChangeListener$Change.getAddedSubList(ListChangeListener.java:242)
at com.sun.javafx.scene.control.behavior.ListViewBehavior.lambda$new$177(ListViewBehavior.java:269)
at javafx.collections.WeakListChangeListener.onChanged(WeakListChangeListener.java:88)
at com.sun.javafx.collections.ListListenerHelper$Generic.fireValueChangedEvent(ListListenerHelper.java:329)
at com.sun.javafx.collections.ListListenerHelper.fireValueChangedEvent(ListListenerHelper.java:73)
at com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList.callObservers(ReadOnlyUnbackedObservableList.java:75)
at javafx.scene.control.MultipleSelectionModelBase.clearAndSelect(MultipleSelectionModelBase.java:378)
at javafx.scene.control.ListView$ListViewBitSetSelectionModel.clearAndSelect(ListView.java:1403)*/

abstract class TransactionDialogFragment(mode: DialogMode) : Fragment() {

    data class AccWrapper(var acc: AnalAcc?) {
        // workaround
        override fun toString() = acc.toString()
    }

    class TransactionDialogModel(t: Transaction?) : ItemViewModel<Transaction>(t) {
        val id = bind(Transaction::id)
        val amount = bind(Transaction::amount)
        //  val maDati = bind(Transaction::maDati)   // workaround
        // val dal = bind(Transaction::dal)
        val document = bind(Transaction::doc)
        val relatedDocument = bind(Transaction::relatedDoc)
        val maDatiWA = SimpleObjectProperty<AccWrapper>(AccWrapper(t?.maDati))// workaround
        val dalWA = SimpleObjectProperty<AccWrapper>(AccWrapper(t?.dal))// workaround
    }

    private val tr = params["tr"] as Transaction?
    val transModel = TransactionDialogModel(tr)
    val doc = params["doc"] as? Document
    val dal = params["dalAnal"] as? AnalAcc

    enum class TransType {
        COMMON_TRANSACTION, TRANSACTION_FOR_DOC, TRANSACTION_FOR_STATM,
    }

    val transType =
            when {
                doc == null -> TransType.COMMON_TRANSACTION
                doc.type == DocType.BANK_STATEMENT -> TransType.TRANSACTION_FOR_STATM
                else -> TransType.TRANSACTION_FOR_DOC
            }

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
                        //      transModel.dal.value = dal // workaround
                        transModel.dalWA.value.acc = dal // workaround
                    }
                }
            DialogMode.UPDATE -> title = Messages.Zmen_transakci.cm()
            DialogMode.DELETE -> title = Messages.Zrus_transakci.cm()
        }

    }

    private fun maDati(): List<AnalAcc> {
        val accs = when (doc?.type) {
            DocType.INCOME -> Facade.pokladny // 211
            else -> Facade.allAccounts
        }
        //  if (accs.size == 1)
        // transModel.maDatiWA.value.acc = accs.first()
        if (transModel.maDatiWA.value.acc == null)
            transModel.maDatiWA.value.acc = accs.first() // workaround
        return accs
    }

    private fun dal(): List<AnalAcc> {
        val accs = when (doc?.type) {
            DocType.INVOICE -> Facade.dodavatele //321
            DocType.OUTCOME -> Facade.pokladny // 211
            else -> Facade.allAccounts
        }
        //    if (accs.size == 1) // workaround
        //        transModel.dal.value = accs.first()
        if (transModel.dalWA.value.acc == null)
            transModel.dalWA.value.acc = accs.first() // workaround
        return accs
    }

    var f: Field? = null


    override val root = form {
        fieldset {
            prefHeight = 350.0
            field(Messages.Doklad.cm().withColon) {
                isDisable = mode == DialogMode.DELETE
                if (transModel.document.value == null) {
                    val ad = Facade.allDocuments
                    val cb = combobox(transModel.document, ad) {
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

            }//.isDisable = mode == DialogMode.DELETE
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
                runAsync {
                    maDati()
                } fail {
                    accError(it)
                } ui {
                    combobox(transModel.maDatiWA, it.map {
                        AccWrapper(it)
                    }) {
                        prefHeight = 50.0
                        //  converter = AnalAccConverter
//                        validator {
//                            if (it == null) error() else null
//                        }

                    }.isDisable = mode == DialogMode.DELETE

                }
            }
            field(Messages.Dal.cm().withColon) {
                runAsync {
                    dal()
                } fail {
                    accError(it)
                } ui {
                    //   combobox(transModel.dal, it) {
                    combobox(transModel.dalWA, it.map { AccWrapper(it) }) {
                        prefHeight = 50.0
//                        converter = AnalAccConverter
//                        validator {
//                            if (it == null) error() else null
//                        }
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
                        find<TransactionsView>().update()
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
                            find<TransactionsView>().update()
                            //PaneTabs.refreshDocAndTransPane()
                            find<TransactionCreateDialog>(params =
                            mapOf("doc" to transModel.document.value,
                                    "dalAnal" to transModel.dalWA.value.acc)).openModal()// workaround
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


