package acc.model

import acc.util.Messages

enum class DocType constructor(val text: String,
                               val abbr: String) {
    INVOICE(Messages.Faktura.cm(), Messages.FAKT.cm()),
    BANK_STATEMENT(Messages.Bankovni_vypis.cm(), Messages.BANKV.cm()),
    INCOME(Messages.Prijmovy_doklad.cm(), Messages.PRIJD.cm()),
    OUTCOME(Messages.Vydajovy_doklad.cm(), Messages.VYDAD.cm()),
    EVENT(Messages.Ucetni_udalost.cm(), Messages.UCETU.cm());
}
