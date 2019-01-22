package acc.model

import acc.util.Messages

enum class DocumentType constructor(
        val text: String) {
    INVOICE(Messages.Faktura.cm()),
    BANK_STATEMENT(Messages.Vypis_z_uctu.cm()),
    INCOME(Messages.Prijmovy_doklad.cm()),
    OUTCOME(Messages.Vydajovy_doklad.cm()),
    EVENT(Messages.Ucetni_udalost.cm())
}
