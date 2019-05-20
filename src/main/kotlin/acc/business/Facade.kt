package acc.business

import acc.integration.AccountCache
import acc.integration.DocumentDAO
import acc.integration.TransDAO
import acc.model.*
import acc.util.AccException
import java.time.LocalDate

object Facade {

    // Ucty
    val allAccounts: List<AnalAcc>
        @Throws(AccException::class)
        get() = AccountCache.allAccs

    val balanceAccounts: List<AnalAcc>
        @Throws(AccException::class)
        get() = AccountCache.balanceAccs

    val dodavatele: List<AnalAcc>  // 321
        get() = AccountCache.dodavatele

    val pokladny: List<AnalAcc>
        get() = AccountCache.pokladny


    @Throws(AccException::class)
    fun createAccount(group: AccGroup, no: String, name: String, initAmount: Long) {
        AccountCache.createAcc(group, no, name, initAmount)
    }

    @Throws(AccException::class)
    fun updateAccount(id: AnalId, name: String, initAmount: Long) {
        AccountCache.updateAcc(id, name, initAmount)
    }

    @Throws(AccException::class)
    fun deleteAccount(id: AnalId) {
        AccountCache.deleteAcc(id)
    }

    fun accountIsUsed(acc: AnalAcc?): Boolean {
        return transactionsByFilter(TransactionFilter(acc = acc)).isNotEmpty()
    }

    // Transakce ***************************************************************
    val allTransactions: List<Transaction>
        @Throws(AccException::class)
        get() = TransDAO.allTrans

    @Throws(AccException::class)
    fun limitTrans(n: Int, offset: Int): List<Transaction> {
        return TransDAO.limitTrans(n, offset)
    }

    @Throws(AccException::class)
    fun createTransaction(amount: Long, madati: AnalAcc, dal: AnalAcc,
                          document: Document, relatedDocument: Document?) {
        TransDAO.createTrans(null, amount, madati,
                dal, document, relatedDocument)
    }


    @Throws(AccException::class)
    fun transactionsByFilter(tf: TransactionFilter?): List<Transaction> {
        return TransDAO.transByFilter(tf)
    }

    @Throws(AccException::class)
    fun updateTransaction(id: TransactionId, amount: Long,
                          madati: AnalAcc, dal: AnalAcc, document: Document,
                          relatedDocument: Document?) {
        TransDAO.updateTrans(id, amount, madati, dal,
                document, relatedDocument)
    }

    @Throws(AccException::class)
    fun deleteTransaction(id: TransactionId) {
        TransDAO.deleteTrans(id)
    }

    // Doklady  ***************************************************************
    val allDocuments: List<Document>
        @Throws(AccException::class)
        get() = DocumentDAO.allDocs

    private val allInvoices: List<Document>
        @Throws(AccException::class)
        get() = DocumentDAO.allDocs
                .filter { it.type == DocType.INVOICE }

    val unpaidInvoices: List<Document>
        @Throws(AccException::class)
        get() {
            val paidInvoices = allTransactions.mapNotNull { it.relatedDoc }
            val ui = allInvoices as MutableList
            ui.removeAll(paidInvoices)
            return ui
        }

    fun documentById(docId: DocId): Document? {
        return DocumentDAO.docById(docId)
    }

    @Throws(AccException::class)
    fun documentsByFilter(docFilter: DocFilter?): List<Document> {
        return if (docFilter == UnpaidInvoicesFilter)
            unpaidInvoices
        else
            DocumentDAO.docsByFilter(docFilter) //as MutableList<Document>
    }

    fun createDocument(type: DocType, number: Int, date: LocalDate, description: String) {
        DocumentDAO.createDoc(type, number, date, description)
    }

    fun updateDocument(id: DocId,
                       date: LocalDate, description: String) {
        DocumentDAO.updateDoc(id, date, description)
    }

    @Throws(AccException::class)
    fun deleteDocument(id: DocId) {
        DocumentDAO.deleteDoc(id)
    }

    fun genDocumentId(docType: DocType): Int {
        return DocumentDAO.findFreeDocId(docType)
    }

}