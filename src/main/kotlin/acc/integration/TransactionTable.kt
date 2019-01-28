package acc.integration

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object TransactionTable : Table() {
    val id = integer("number").autoIncrement().primaryKey()
    val amount = long("amount")
    val maDatiSyntAcc = varchar("maDatiSyntAcc", 3)
    val maDatiAnal = varchar("maDatiAnal", 3)
    val dalSyntAcc = varchar("dalSyntAcc", 3)
    val dalAnal = varchar("dalAnal", 3)
    val documentType = varchar("documentType", 20)
    val documentNumber = integer("documentNumber")
    val relatedDocumentType:Column<String> = varchar("relatedDocumentType", 20)
    val relatedDocumentNumber:Column<Int> = integer("relatedDocumentNumber")
}