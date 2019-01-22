package acc.model

import acc.util.Messages

enum class DocumentType constructor(
        val text: String) {
    INVOICE(Messages.Fak.cm()),
    BANK_STATEMENT(Messages.Vyp.cm()),
    INCOME(Messages.Pri.cm()),
    OUTCOME(Messages.Vyd.cm()),
    EVENT(Messages.Uda.cm())
}
