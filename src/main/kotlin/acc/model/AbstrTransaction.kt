package acc.model

abstract class AbstrTransaction(
        val id: TransactionId,
        val amount: Long,
        val maDati: AnalAcc,
        val dal: AnalAcc)