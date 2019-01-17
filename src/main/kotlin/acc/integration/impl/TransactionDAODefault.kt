package acc.integration.impl

import acc.integration.AccountDAO
import acc.integration.TransactionDAO
import acc.model.*
import acc.util.AccException
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.streams.toList

class TransactionDAODefault : TransactionDAO() {

    private val transactionMap = TreeMap<TransactionId, AbstrTransaction>()

    init {
        try {
            val dodavatele = AccountDAODefault.getByNumber("321001").get()
            val fio = AccountDAODefault.getByNumber("221001").get()
            val material = AccountDAODefault.getByNumber("501001").get()
            val f1 = DocumentDAO.instance.getByRegexp("F1")[0]
            val v1 = DocumentDAO.instance.getByRegexp("V1")[0]

            // init
            createInit(100L, AccountDAODefault.pocatecniUcetRozvazny, dodavatele)
            createInit(500L, fio, AccountDAODefault.pocatecniUcetRozvazny)
            createTransaction(100L, material, dodavatele, f1, null)
            createTransaction(100L, dodavatele, fio, v1, f1)
            //            createTransaction(Optional.of(LocalDate.of(2019, 1, 6)), 50L, material, dodavatele, "f2", "faktura", "");
        } catch (ex: AccException) {
            Logger.getLogger(TransactionDAO::class.java.name).log(Level.SEVERE, null, ex)
        }

    }

    override  fun createInit(amount: Long, maDati: AnalAcc,dal: AnalAcc) {
        val i = Init(TransactionId(keyC++), amount, maDati, dal)
        transactionMap[i.id] = i
    }


    @Throws(AccException::class)
    override fun createTransaction(amount: Long, madati: AnalAcc,
                                   dal: AnalAcc, document: Document, bindingDocument: Document?) {
        val r = Transaction(TransactionId(keyC++), amount,
                madati, dal, document, bindingDocument)
        transactionMap[r.id] = r
    }

    @Throws(AccException::class)
    override fun all(): List<Transaction> {
        return transactionMap.values.stream()
                .filter { it is Transaction }
                .map { it as Transaction }
                .toList()
    }

    @Throws(AccException::class)
    override fun get(tf: TransactionFilter): List<Transaction> {
        return transactionMap.values
                .stream()
                .filter { it is Transaction }
                .map { it as Transaction }
                .filter { tf.match(it) }
                .toList()
    }

    @Throws(AccException::class)
    override fun delete(id: TransactionId) {
        transactionMap.remove(id)
    }

    @Throws(AccException::class)
    override fun update(id: TransactionId, amount: Long,
                        madati: AnalAcc, dal: AnalAcc, document: Document,
                        bindingDocument: Document) {
        val r = Transaction(id, amount, madati,
                dal, document, bindingDocument)
        transactionMap.replace(id, r)
    }


    @Throws(AccException::class)
    override fun getInits(acc: AnalAcc?): List<Init> {
        return transactionMap.values.stream()
                .filter { it is Init }
                .map { it as Init }
                .toList()
    }


    companion object {

        internal var keyC = 1

    }

}
