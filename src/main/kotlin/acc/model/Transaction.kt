package acc.model

data class Transaction(
        val id: TransactionId,
        val amount: Long,
        val maDati: AnalAcc,
        val dal: AnalAcc,
        val doc: Document,
        val relatedDocId: Document? = null)