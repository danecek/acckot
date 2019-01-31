package acc.integration

import acc.model.*
import acc.util.AccException

interface TransactionDAOInterface {


    @Throws(AccException::class)
    fun createTransaction(id: TransactionId?, amount: Long, maDati: AnalAcc,
                          dal: AnalAcc, document: Document, relatedDocument: Document?)

    val allTransaction: List<Transaction>

    @Throws(AccException::class)
    fun getTrans(tf: TransactionFilter?): List<Transaction>

    @Throws(AccException::class)
    fun deleteTrans(id: TransactionId)

    @Throws(AccException::class)
    fun updateTrans(id: TransactionId, amount: Long,
                    maDati: AnalAcc, dal: AnalAcc, document: Document,
                    relatedDocument: Document?)

}