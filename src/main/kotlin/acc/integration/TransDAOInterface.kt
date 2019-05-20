package acc.integration

import acc.model.*
import acc.util.AccException

interface TransDAOInterface {


    @Throws(AccException::class)
    fun createTrans(id: TransactionId?, amount: Long, maDati: AnalAcc,
                    dal: AnalAcc, document: Document, relatedDocument: Document?)

    val allTrans: List<Transaction>

    @Throws(AccException::class)
    fun limitTrans(n:Int, offset:Int): List<Transaction>

    @Throws(AccException::class)
    fun transByFilter(tf: TransactionFilter?): List<Transaction>

    @Throws(AccException::class)
    fun deleteTrans(id: TransactionId)

    @Throws(AccException::class)
    fun updateTrans(id: TransactionId, amount: Long,
                    maDati: AnalAcc, dal: AnalAcc, document: Document,
                    relatedDocument: Document?)

}