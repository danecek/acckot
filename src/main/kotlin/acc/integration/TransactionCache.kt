/*
package acc.integration

import acc.model.*
import acc.util.AccException
import java.util.*


object TransactionCachex : TransDAOInterface {

    private val transactionMap = TreeMap<TransactionId, Transaction>()

    fun dataInit() {
        transactionMap.clear()
    }

    init {
*/
/*        val dodavatele = AccountCache.accById(AnalId(Osnova.dodavatele, "001"))!!
        val fio = AccountCache.accById(AnalId(Osnova.banka, "001"))!!
        val material = AccountCache.accById(AnalId(Osnova.material, "001"))!!
        val f1 = DocumentDAO.accById(DocId(DocType.INVOICE, 1))!!
        val v1 = DocumentDAO.accById(DocId(DocType.BANK_STATEMENT, 1))!!

        // initAmount
        createInit(100L, AccountCache.pocatecniUcetRozvazny, dodavatele)
        createInit(500L, fio, AccountCache.pocatecniUcetRozvazny)
        createTrans(TransactionId(1),100L, material, dodavatele, f1, null)
        createTrans(TransactionId(2),100L, dodavatele, fio, v1, f1)*//*

    }


    @Throws(AccException::class)
    override fun createTrans(id: TransactionId?, amount: Long, maDati: AnalAcc,
                                   dal: AnalAcc, document: Document, relatedDocument: Document?) {
        val r = Transaction(id!!, amount,
                maDati, dal, document, relatedDocument)
        transactionMap[r.id] = r
    }

    override val allTrans: List<Transaction>
        @Throws(AccException::class)
        get() = transactionMap.values.toList()


    @Throws(AccException::class)
    override fun getTrans(tf: TransactionFilter?): List<Transaction> {
        return transactionMap.values
                .filter { tf?.match(it)?:true }
    }

    @Throws(AccException::class)
    override fun deleteTrans(id: TransactionId) {
        transactionMap.remove(id)
    }

    @Throws(AccException::class)
    override fun updateTrans(id: TransactionId, amount: Long,
                             maDati: AnalAcc, dal: AnalAcc, document: Document,
                             relatedDocument: Document?) {
        val r = Transaction(id, amount, maDati,
                dal, document, relatedDocument)
        transactionMap.replace(id, r)
    }

}
*/
