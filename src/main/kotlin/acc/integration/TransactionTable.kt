package acc.integration

import org.jetbrains.exposed.dao.IntIdTable

object TransactionTable : IntIdTable() {
    val amount = long("amount")
    val maDati = varchar("maDati", 7)
    val dal = varchar("dal", 7)
    val documentId = integer("documentId")
    val relatedDocumentId = integer("relatedDocumentId")
}