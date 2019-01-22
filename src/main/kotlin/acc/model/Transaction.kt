package acc.model

class Transaction(
        id: TransactionId,
        amount: Long,
        maDati: AnalAcc,
        dal: AnalAcc,
        val document: Document,
        val relatedDocument: Document? = null) : AbstrTransaction(id, amount, maDati, dal)