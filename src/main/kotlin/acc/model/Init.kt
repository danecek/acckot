package acc.model

class Init(id: TransactionId,
           amount: Long,
           maDati: AnalAcc,
           dal: AnalAcc) : AbstrTransaction(id, amount, maDati, dal)