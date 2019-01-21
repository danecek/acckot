package acc.integration

import acc.integration.impl.AccountDAODefault
import acc.integration.impl.DocumentDAO
import acc.model.*
import acc.util.AccException
import java.time.LocalDate
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.streams.toList

object TransactionDAO {

    private val transactionMap = TreeMap<TransactionId, AbstrTransaction>()

    init {
        try {
            val dodavatele = AccountDAODefault.getByNumber("321001").get()
            val fio = AccountDAODefault.getByNumber("221001").get()
            val material = AccountDAODefault.getByNumber("501001").get()
/*            DocumentDAO.create(DocumentType.INVOICE, 1, LocalDate.now(), "xxxxxx")
            DocumentDAO.create(DocumentType.BANK_STATEMENT, 1, LocalDate.now(), "yyyyyy")
            println(DocumentDAO.all)*/
            val f1 = DocumentDAO.getById(DocumentId(DocumentType.INVOICE, 1))!!
            val v1 = DocumentDAO.getById(DocumentId(DocumentType.BANK_STATEMENT, 1))!!

            // init
            createInit(100L, AccountDAODefault.pocatecniUcetRozvazny, dodavatele)
            createInit(500L, fio, AccountDAODefault.pocatecniUcetRozvazny)
            createTransaction(100L, material, dodavatele, f1, null)
            createTransaction(100L, dodavatele, fio, v1, f1)
        } catch (ex: AccException) {
            Logger.getLogger(TransactionDAO::class.java.name).log(Level.SEVERE, null, ex)
        }

    }

    fun createInit(amount: Long, maDati: AnalAcc, dal: AnalAcc) {
        val i = Init(genKey(), amount, maDati, dal)
        transactionMap[i.id] = i
    }


    @Throws(AccException::class)
    fun createTransaction(amount: Long, madati: AnalAcc,
                          dal: AnalAcc, document: Document, bindingDocument: Document?) {
        val r = Transaction(genKey(), amount,
                madati, dal, document, bindingDocument)
        transactionMap[r.id] = r
    }

    @Throws(AccException::class)
    fun all(): List<Transaction> {
        return transactionMap.values.stream()
                .filter { it is Transaction }
                .map { it as Transaction }
                .toList()
    }

    @Throws(AccException::class)
    fun get(tf: TransactionFilter): List<Transaction> {
        return transactionMap.values
                .stream()
                .filter { it is Transaction }
                .map { it as Transaction }
                .filter { tf.match(it) }
                .toList()
    }

    @Throws(AccException::class)
    fun delete(id: TransactionId) {
        transactionMap.remove(id)
    }

    @Throws(AccException::class)
    fun update(id: TransactionId, amount: Long,
               madati: AnalAcc, dal: AnalAcc, document: Document,
               bindingDocument: Document) {
        val r = Transaction(id, amount, madati,
                dal, document, bindingDocument)
        transactionMap.replace(id, r)
    }


    @Throws(AccException::class)
    fun getInits(acc: AnalAcc?): List<Init> {
        return TransactionDAO.transactionMap.values.stream()
                .filter { it is Init }
                .filter { acc == null || it.maDati == acc || it.dal == acc }
                .map { it as Init }
                .peek { println(it) }
                .toList()
    }

    private fun genKey(): TransactionId {
        return if (transactionMap.isEmpty())
            TransactionId(1)
        else TransactionId(transactionMap.lastKey().id + 1)
    }


}
