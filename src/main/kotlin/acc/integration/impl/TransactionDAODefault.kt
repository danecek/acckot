package acc.integration.impl

import acc.integration.TransactionDAO
import acc.model.AnalAcc
import acc.model.Document
import acc.model.Transaction
import acc.model.TransactionId
import acc.util.AccException

import java.time.LocalDate
import java.util.Optional
import java.util.TreeMap
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.streams.toList

class TransactionDAODefault : TransactionDAO() {

    private val transactionMap = TreeMap<TransactionId, Transaction>()

    override val all: List<Transaction>
        @Throws(AccException::class)
        get() = transactionMap.values.stream().toList()

    init {
        try {
            val dodavatele = AcountDAODefault.getByNumber("321001").get()
            val fio = AcountDAODefault.getByNumber("221001").get()
            val material = AcountDAODefault.getByNumber("501001").get()
            val f1 = DocumentDAO.instance.getByRegexp("F1")[0]
            val v1 = DocumentDAO.instance.getByRegexp("V1")[0]

            // init
            createInitDal(100L, dodavatele)
            createInitMaDati(500L, fio)
            create(100L, material, dodavatele, Optional.of(f1), Optional.empty())
            create(100L, dodavatele, fio, Optional.of(v1), Optional.of(f1))
            //            create(Optional.of(LocalDate.of(2019, 1, 6)), 50L, material, dodavatele, "f2", "faktura", "");
        } catch (ex: AccException) {
            Logger.getLogger(TransactionDAODefault::class.java.name).log(Level.SEVERE, null, ex)
        }

    }

    private fun createInitMaDati(amount: Long, maDati: AnalAcc) {
        try {
            create(amount, maDati, AcountDAODefault.pocatecniUcetRozvazny, Optional.empty(), Optional.empty())
        } catch (ex: AccException) {
            Logger.getLogger(TransactionDAODefault::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    private fun createInitDal(amount: Long, dal: AnalAcc) {
        try {
            create(amount, AcountDAODefault.pocatecniUcetRozvazny, dal, Optional.empty(), Optional.empty())
        } catch (ex: AccException) {
            Logger.getLogger(TransactionDAODefault::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    @Throws(AccException::class)
    override fun create(amount: Long, madati: AnalAcc,
                        dal: AnalAcc, document: Optional<Document>, bindingDocument: Optional<Document>) {
        val r = Transaction(TransactionId(keyC++), amount,
                madati, dal, document, bindingDocument)
        transactionMap[r.id] = r
    }

    @Throws(AccException::class)
    override fun delete(id: TransactionId) {
        transactionMap.remove(id)
    }

    @Throws(AccException::class)
    override fun update(id: TransactionId, amount: Long,
                        madati: AnalAcc, dal: AnalAcc, document: Optional<Document>,
                        bindingDocument: Optional<Document>) {
        val r = Transaction(id, amount, madati,
                dal, document, bindingDocument)
        transactionMap.replace(id, r)
    }

    @Throws(AccException::class)
    override fun get(optOd: Optional<LocalDate>, optDo: Optional<LocalDate>,
                     acc: Optional<AnalAcc>, optDocument: Optional<Document>,
                     optRelatedDocument: Optional<Document>): List<Transaction> {
        return transactionMap.values.stream()
                .filter { !optOd.isPresent || it.date.isPresent }
                .filter {
                    !optOd.isPresent
                            || !it.date.isPresent
                            || it.date.get().isAfter(optOd.get().minusDays(1))
                }
                .filter { t ->
                    (!optDo.isPresent
                            || !t.date.isPresent
                            || t.date.isPresent && t.date.get().isBefore(optDo.get().plusDays(1)))
                }
                .filter {
                    !acc.isPresent || acc.get().id == it.maDati.id || acc.get().id == it.dal.id
                }
                .filter { impl(optDocument, it.document) }
                .filter { impl(optRelatedDocument, it.relatedDocument) }
                .toList()
    }

    @Throws(AccException::class)
    override fun getInits(acc: Optional<AnalAcc>): List<Transaction> {
        return transactionMap.values.stream()
                .filter { !it.date.isPresent }
                .filter {
                    !acc.isPresent || acc.get().id == it.maDati.id || acc.get().id == it.dal.id
                }
                .toList()//      .collect<List<Transaction>, Any>(Collectors.toList())
    }

    companion object {

        internal var keyC = 1

        private fun <T> impl(x: Optional<T>, y: Optional<T>): Boolean {
            return !x.isPresent || x == y
        }
    }

}
