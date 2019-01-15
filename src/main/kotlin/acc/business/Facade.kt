package acc.business

import acc.business.balance.BalanceItem
import acc.business.balance.Balance
import acc.integration.AccountDAO
import acc.integration.TransactionDAO
import acc.integration.impl.AcountDAODefault
import acc.integration.impl.DocumentDAO
import acc.model.*
import acc.util.AccException
import java.time.LocalDate
import java.time.Month
import java.util.Optional

object Facade {

    val allAccounts: List<AnalAcc>
        @Throws(AccException::class)
        get() {
            val result = AcountDAODefault.all
            return result
        }

    val balanceAccounts: List<AnalAcc>
        @Throws(AccException::class)
        get() = AcountDAODefault.balancing

    val allTransactions: List<Transaction>
        @Throws(AccException::class)
        get() = TransactionDAO.instance.all

    val allDocuments: List<Document>
        @Throws(AccException::class)
        get() = DocumentDAO.instance.all

    @Throws(AccException::class)
    fun createAccount(skupina: AccGroup, no: String, name: String) {
        AcountDAODefault.create(skupina, no, name)
    }

    @Throws(AccException::class)
    fun pur(): AnalAcc {
        return AcountDAODefault.pocatecniUcetRozvazny
    }

    @Throws(AccException::class)
    fun deleteAccount(id: AnalId) {
        AcountDAODefault.delete(id)
    }

    @Throws(AccException::class)
    fun getAccountByNumber(number: String): Optional<AnalAcc> {
        return AcountDAODefault.getByNumber(number)
    }

    @Throws(AccException::class)
    fun getAccountsByGroup(accg: AccGroup): List<AnalAcc> {
        return AcountDAODefault.getByGroup(accg)
    }

    @Throws(AccException::class)
    fun getAccountsByClass(accg: AccGroup): List<AnalAcc> {
        return AcountDAODefault.getByClass(accg)
    }

    @Throws(AccException::class)
    fun createTransaction(amount: Long, madati: AnalAcc, dal: AnalAcc, document: Optional<Document>, bindingDocument: Optional<Document>) {
        TransactionDAO.instance.create(amount, madati,
                dal, document, bindingDocument)
    }

    @Throws(AccException::class)
    fun updateTransaction(id: TransactionId, amount: Long,
                          madati: AnalAcc, dal: AnalAcc, document: Optional<Document>,
                          bindingDocument: Optional<Document>) {
        TransactionDAO.instance.update(id, amount, madati, dal,
                document, bindingDocument)
    }

    @Throws(AccException::class)
    fun deleteTransaction(id: TransactionId) {
        TransactionDAO.instance.delete(id)
    }

    @Throws(AccException::class)
    fun getTransactions(optOd: Optional<LocalDate>, optDo: Optional<LocalDate>,
                        acc: Optional<AnalAcc>, optDocument: Optional<Document>, optRelatedDocument: Optional<Document>): List<Transaction> {
        return TransactionDAO.instance.get(optOd, optDo, acc, optDocument, optRelatedDocument)
    }

    @Throws(AccException::class)
    fun createBalance(month: Month): List<BalanceItem> {
        return Balance.instance.createBalance(month)
    }

    @Throws(AccException::class)
    fun createInit(amount: Long, acc: AnalAcc, madatiDal: MadatiDal) {
        when (madatiDal) {
            MadatiDal.MA_DATI -> {
                TransactionDAO.instance
                        .create(amount, acc, pur(), Optional.empty(), Optional.empty())
            }
            MadatiDal.DAL -> {
                TransactionDAO.instance
                        .create(amount, pur(), acc, Optional.empty(), Optional.empty())
            }
        }
    }

    @Throws(AccException::class)
    fun getInits(acc: Optional<AnalAcc>): List<Transaction> {
        return TransactionDAO.instance.getInits(acc)
    }

    // Document ***************************************************************
    fun createDocument(type: DocumentType, name: String,
                       date: LocalDate, description: String) {
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
