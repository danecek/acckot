package acc.business

import acc.business.balance.Balance
import acc.business.balance.BalanceItem
import acc.integration.TransactionDAO
import acc.integration.impl.AccountDAODefault
import acc.integration.impl.DocumentDAO
import acc.model.*
import acc.util.AccException
import java.time.LocalDate
import java.time.Month
//import java.util.*
import kotlin.streams.toList

object Facade {

    // Ucty
    val allAccounts: List<AnalAcc>
        @Throws(AccException::class)
        get() = AccountDAODefault.all

    val balanceAccounts: List<AnalAcc>
        @Throws(AccException::class)
        get() = AccountDAODefault.balancing


    val dodavatele: List<AnalAcc>  // 321
        get() = AccountDAODefault.dodavatele

    val pokladna: List<AnalAcc>
        get() = AccountDAODefault.pokladna


    @Throws(AccException::class)
    fun createAccount(group: AccGroup, no: String, name: String) {
        AccountDAODefault.create(group, no, name)
    }

    @Throws(AccException::class)
    fun pur(): AnalAcc {
        return AccountDAODefault.pocatecniUcetRozvazny
    }

    @Throws(AccException::class)
    fun getAccountsByGroup(accg: AccGroup): List<AnalAcc> {
        return AccountDAODefault.getByGroup(accg)
    }

    @Throws(AccException::class)
    fun getAccountsByClass(accg: AccGroup): List<AnalAcc> {
        return AccountDAODefault.getByClass(accg)
    }

    @Throws(AccException::class)
    fun updateAccount(group: AccGroup, no: String, name: String) {
        AccountDAODefault.update(group, no, name)
    }

    @Throws(AccException::class)
    fun deleteAccount(id: AnalId) {
        AccountDAODefault.delete(id)
    }

    fun accountIsUsed(acc: AnalAcc?): Boolean {
       return getTransactions(TransactionFilter(account= acc)).isNotEmpty() ||
               getInits(acc).isNotEmpty()
    }

    // Transakce
    @Throws(AccException::class)
    fun createTransaction(amount: Long, madati: AnalAcc, dal: AnalAcc,
                          document: Document, relatedDocument: Document?) {
        TransactionDAO.createTransaction(amount, madati,
                dal, document, relatedDocument)
    }

    @Throws(AccException::class)
    fun getTransactions(tf: TransactionFilter): List<Transaction> {
        return TransactionDAO.get(tf)
    }

    val allTransactions: List<Transaction>
        @Throws(AccException::class)
        get() = TransactionDAO.all()


    @Throws(AccException::class)
    fun updateTransaction(id: TransactionId, amount: Long,
                          madati: AnalAcc, dal: AnalAcc, document: Document,
                          bindingDocument: Document) {
        TransactionDAO.update(id, amount, madati, dal,
                document, bindingDocument)
    }

    @Throws(AccException::class)
    fun deleteTransaction(id: TransactionId) {
        TransactionDAO.delete(id)
    }

    // Init ***************************************************************
    @Throws(AccException::class)
    fun getInits(acc: AnalAcc? = null): List<Init> {
        return TransactionDAO.getInits(acc)
    }

    @Throws(AccException::class)
    fun createInit(amount: Long, acc: AnalAcc, madatiDal: MadatiDal) {
        when (madatiDal) {
            MadatiDal.MA_DATI -> {
                TransactionDAO
                        .createInit(amount, acc, pur())
            }
            MadatiDal.DAL -> {
                TransactionDAO
                        .createInit(amount, pur(), acc)
            }
        }
    }

    fun deleteInit(amount: Long, acc: AnalAcc, madatiDal: MadatiDal) {
        when (madatiDal) {
            MadatiDal.MA_DATI -> {
                TransactionDAO
                        .createInit(amount, acc, pur())
            }
            MadatiDal.DAL -> {
                TransactionDAO
                        .createInit(amount, pur(), acc)
            }
        }
    }




    // Document ***************************************************************
    fun createDocument(type: DocumentType, number: Int, date: LocalDate, description: String) {
        DocumentDAO.create(type, number, date, description)
    }

    val allDocuments: List<Document>
        @Throws(AccException::class)
        get() = DocumentDAO.all

    val allInvoices: List<Document>
        @Throws(AccException::class)
        get() = DocumentDAO.all
                .filter { it.type == DocumentType.INVOICE }

    val unpaidInvoices: List<Document>
        @Throws(AccException::class)
        get() {
            val paidInvoices = allTransactions.stream()
                    .map { it -> it.relatedDocument }
                    .filter{it !=null}
            (allInvoices as MutableList).removeAll(paidInvoices.toList())
            return allInvoices
        }

    fun getDocumentById(docId: DocumentId): Document? {
        return DocumentDAO.getById(docId)
    }

    @Throws(AccException::class)
    fun getDocumentByRegexp(regexp: String): List<Document> {
        return DocumentDAO.getByRegexp(regexp)
    }

    fun updateDocument(id: DocumentId,
                       date: LocalDate, description: String) {
        DocumentDAO.update(id, date, description)
    }

    @Throws(AccException::class)
    fun deleteDocument(id: DocumentId) {
        DocumentDAO.delete(id)
    }


    fun genDocumentId(docType: DocumentType): Int {
        return DocumentDAO.findFreeDocumentId(docType)
    }


    // Rozvaha ***************************************************************

    @Throws(AccException::class)
    fun createBalance(month: Month): List<BalanceItem> {
        return Balance().createBalance(month)
    }



    val POCATECNI_STAV = "pocatecni stav"



}
