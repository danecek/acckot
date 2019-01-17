package acc.integration

import acc.integration.impl.TransactionDAODefault
import acc.model.*
import acc.util.AccException

abstract class TransactionDAO {

    @Throws(AccException::class)
    abstract fun createTransaction(amount: Long, madati: AnalAcc,
                                   dal: AnalAcc, document: Document, bindingDocument: Document?)


    @Throws(AccException::class)
    abstract fun all(): List<Transaction>

    @Throws(AccException::class)
    abstract fun get(tf: TransactionFilter): List<Transaction>

    @Throws(AccException::class)
    abstract fun update(id: TransactionId, amount: Long,
                        madati: AnalAcc, dal: AnalAcc, document: Document,
                        bindingDocument: Document)

    @Throws(AccException::class)
    abstract fun delete(id: TransactionId)

    @Throws(AccException::class)
    abstract fun createInit(amount: Long, maDati: AnalAcc, dal:AnalAcc)

    @Throws(AccException::class)
    abstract fun getInits(acc: AnalAcc?): List<Init>

    companion object {
        val instance = TransactionDAODefault()
    }
}
