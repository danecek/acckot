package acc.business

import acc.business.balance.BalanceItem
import acc.business.balance.Balance
import acc.integration.TransactionDAO
import acc.integration.impl.AccountDAODefault
import acc.integration.impl.DocumentDAO
import acc.model.*
import acc.util.AccException
import java.time.LocalDate
import java.time.Month
import java.util.Optional

object Facade {

    // Ucty
    val allAccounts: List<AnalAcc>
        @Throws(AccException::class)
        get() {
            val result = AccountDAODefault.all
            return result
        }

    val balanceAccounts: List<AnalAcc>
        @Throws(AccException::class)
        get() = AccountDAODefault.balancing


    val allDocuments: List<Document>
        @Throws(AccException::class)
        get() = DocumentDAO.instance.all

    @Throws(AccException::class)
    fun createAccount(skupina: AccGroup, no: String, name: String) {
        AccountDAODefault.create(skupina, no, name)
    }

    @Throws(AccException::class)
    fun pur(): AnalAcc {
        return AccountDAODefault.pocatecniUcetRozvazny
    }

    @Throws(AccException::class)
    fun deleteAccount(id: AnalId) {
        AccountDAODefault.delete(id)
    }

    @Throws(AccException::class)
    fun getAccountByNumber(number: String): Optional<AnalAcc> {
        return AccountDAODefault.getByNumber(number)
    }

    @Throws(AccException::class)
    fun getAccountsByGroup(accg: AccGroup): List<AnalAcc> {
        return AccountDAODefault.getByGroup(accg)
    }

    @Throws(AccException::class)
    fun getAccountsByClass(accg: AccGroup): List<AnalAcc> {
        return AccountDAODefault.getByClass(accg)
    }
    // Transakce
    @Throws(AccException::class)
    fun createTransaction(amount: Long, madati: AnalAcc, dal: AnalAcc,
                          document: Document, relatedDocument: Document?) {
        TransactionDAO.instance.createTransaction(amount, madati,
                dal, document, relatedDocument)
    }

    @Throws(AccException::class)
    fun getTransactions(tf:TransactionFilter): List<Transaction> {
        return TransactionDAO.instance.get(tf)
    }

    val allTransactions: List<Transaction>
        @Throws(AccException::class)
        get() = TransactionDAO.instance.all()


    @Throws(AccException::class)
    fun updateTransaction(id: TransactionId, amount: Long,
                          madati: AnalAcc, dal: AnalAcc, document: Document,
                          bindingDocument: Document) {
        TransactionDAO.instance.update(id, amount, madati, dal,
                document, bindingDocument)
    }

    @Throws(AccException::class)
    fun deleteTransaction(id: TransactionId) {
        TransactionDAO.instance.delete(id)
    }

    @Throws(AccException::class)
    fun getInits(acc: AnalAcc?): List<Init> {
        return TransactionDAO.instance.getInits(acc)
    }


    // Rozvaha

    @Throws(AccException::class)
    fun createBalance(month: Month): List<BalanceItem> {
        return Balance.instance.createBalance(month)
    }

    @Throws(AccException::class)
    fun createInit(amount: Long, acc: AnalAcc, madatiDal: MadatiDal) {
        when (madatiDal) {
            MadatiDal.MA_DATI -> {
                TransactionDAO.instance
                        .createInit(amount, acc, pur())
            }
            MadatiDal.DAL -> {
                TransactionDAO.instance
                        .createInit(amount, pur(), acc)
            }
        }
    }



    // Document ***************************************************************
    fun createDocument(type: DocumentType, date: LocalDate, name: String, description: String) {
        DocumentDAO.instance.create(type, name, date, description)
    }

    fun updateDocument(id: DocumentId,
                       date: LocalDate, description: String) {
        DocumentDAO.instance.update(id,date, description)
    }

    @Throws(AccException::class)
    fun deleteDocument(id: DocumentId) {
        DocumentDAO.instance.delete(id)
    }

    @Throws(AccException::class)
    fun getDocumentByName(name: String): List<Document> {
        return DocumentDAO.instance.getByRegexp(name)
    }


    val POCATECNI_STAV = "pocatecni stav"


}
