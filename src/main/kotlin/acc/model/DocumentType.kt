package acc.model

import acc.util.Messages

enum class DocumentType private constructor(
        val text: String) {
    INVOICE(Messages.Faktura.cm()),
    BANK_STATEMENT(Messages.Vypis_z_uctu.cm()),
    ELSE(Messages.Ostatni.cm())
}
