package acc.integration

import acc.integration.impl.TransactionDAODefault
import acc.model.AnalAcc
import acc.model.Document
import acc.model.Transaction
import acc.model.TransactionId
import acc.util.AccException
import java.time.LocalDate
import java.util.Optional

abstract class TransactionDAO {

    abstract val all: List<Transaction>

    @Throws(AccException::class)
    abstract fun create(amount: Long, madati: AnalAcc,
                        dal: AnalAcc, document: Optional<Document>, bindingDocument: Optional<Document>)

    @Throws(AccException::class)
    abstract fun update(id: TransactionId, amount: Long,
                        madati: AnalAcc, dal: AnalAcc, document: Optional<Document>,
                        bindingDocument: Optional<Document>)

    @Throws(AccException::class)
    abstract fun delete(id: TransactionId)

    @Throws(AccException::class)
    abstract operator fun get(optOd: Optional<LocalDate>, optDo: Optional<LocalDate>,
                              acc: Optional<AnalAcc>, optDocument: Optional<Document>, optRelatedDocument: Optional<Document>): List<Transaction>

    @Throws(AccException::class)
    abstract fun getInits(acc: Optional<AnalAcc>): List<Transaction>

    companion object {

        var instance: TransactionDAO = TransactionDAODefault()
    }
}
