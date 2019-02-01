package acc.model

data class Transaction(
        val id: TransactionId,
        var amount: Long,
        var maDati: AnalAcc,
        var dal: AnalAcc,
        var doc: Document,
        var relatedDoc: Document? = null)