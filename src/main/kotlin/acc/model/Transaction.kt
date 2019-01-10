package acc.model

import java.time.LocalDate
import java.util.Optional
import acc.util.Global

open class Transaction(
        val id: TransactionId,
        val amount: Long,
        val maDati: AnalAcc,
        val dal: AnalAcc,
        val document: Optional<Document>,
        val relatedDocument: Optional<Document>) {
        val date: Optional<LocalDate>
        get() = if (!document.isPresent) {
            Optional.empty()
        } else {
            Optional.of(document.get().date)
        }

    val dateText = if (!document.isPresent) {
        ""
    } else document.get().date.format(Global.df)

    val isInit = !document.isPresent
}