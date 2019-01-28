package acc.model

import acc.util.Messages

enum class DocType constructor(val text: String,
                               val abbr: String) {
    INVOICE(Messages.Faktura.cm(), Messages.FAK.cm()),
    BANK_STATEMENT(Messages.Bankovni_vypis.cm(), Messages.VYP.cm()),
    INCOME(Messages.Prijmovy_doklad.cm(), Messages.PRI.cm()),
    OUTCOME(Messages.Vydajovy_doklad.cm(), Messages.VYD.cm()),
    EVENT(Messages.Ucetni_udalost.cm(), Messages.UU.cm());
}
