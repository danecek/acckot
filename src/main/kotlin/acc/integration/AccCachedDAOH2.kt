package acc.integration

import acc.Options
import acc.model.*
import acc.model.Transaction
import acc.util.AccException
import acc.util.toDateTime
import acc.util.toLocalDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object AccCachedDAOH2 : DocumentDAOInterface, TransDAOInterface {
    init {
        Database.connect(url = "jdbc:h2:/" + Options.h2File,
                driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(TransactionTable, DocumentTable)
        }
    }

    // Transakce ***************************************************************************
    override fun transByFilter(tf: TransactionFilter?): List<Transaction> {
        return allTrans.filter {
            tf?.match(it) != false
        }.toMutableList()
    }

    override val allTrans: List<Transaction>
        get() {
            val docMap = mutableMapOf<Int, Document>()
            allDocs.forEach {
                docMap.put(it.id, it)
            }
            return transaction {
                TransactionTable.selectAll().map {
                    Transaction(
                            id = TransactionId(it[TransactionTable.id].value),
                            amount = it[TransactionTable.amount],
                            maDati = AccountCache.accByNumber(it[TransactionTable.maDati]),
                            dal = AccountCache.accByNumber(it[TransactionTable.dal]),
                            doc = docMap[it[TransactionTable.documentId]]!!,
                            relatedDoc = docMap[it[TransactionTable.relatedDocumentId]]
                    )
                }
            }
        }


    @Throws(AccException::class)
    override fun createTrans(id: TransactionId?, amount: Long, maDati: AnalAcc,
                             dal: AnalAcc, document: Document, relatedDocument: Document?) {
        assert(id == null)
        transaction {
            TransactionTable.insert {
                it[this.amount] = amount
                it[this.maDati] = maDati.number
                it[this.dal] = dal.number
                it[this.documentId] = document.id//DocumentTable.id
                it[this.relatedDocumentId] = relatedDocument?.id ?: 0//DocumentTable.id
            }
        }
    }

    @Throws(AccException::class)
    override fun updateTrans(id: TransactionId, amount: Long,
                             maDati: AnalAcc, dal: AnalAcc, document: Document,
                             relatedDocument: Document?) {
        transaction {
            TransactionTable.update({
                (TransactionTable.id eq id.id)
            }) {
                it[this.amount] = amount
                it[this.maDati] = maDati.number
                it[this.dal] = dal.number
                it[this.documentId] = document.id
                it[this.relatedDocumentId] = relatedDocument?.id ?: 0
            }
        }
    }

    @Throws(AccException::class)
    override fun deleteTrans(id: TransactionId) {
        transaction {
            assert(TransactionTable.deleteWhere {
                TransactionTable.id eq id.id
            } == 0)
        }
    }

    // Doklady ***************************************************************************
    override val allDocs: List<Document>
        get() =
            transaction {
                DocumentTable.selectAll()
                        .map {
                            Document(
                                    it[DocumentTable.id].value,
                                    DocType.valueOf(it[DocumentTable.typeName]),
                                    it[DocumentTable.number],
                                    toLocalDate(it[DocumentTable.date]),
                                    it[DocumentTable.description])
                        }.toMutableList()
            }


    override fun docsByFilter(docFilter: DocFilter?): List<Document> {
        return allDocs.filter { docFilter?.matchDoc(it) != false }.toMutableList()
    }

    @Throws(AccException::class)
    override fun createDoc(type: DocType, number: Int, date: LocalDate,
                           description: String) : Int {
        return transaction {
            DocumentTable.insertAndGetId {
                it[this.typeName] = type.name
                it[this.number] = number
                it[this.date] = toDateTime(date)
                it[this.description] = description
            }.value

        }
    }

    override fun updateDoc(doc: Document, date: LocalDate, description: String) {
        transaction {
            assert(DocumentTable.update({
                (DocumentTable.id eq doc.id)// and (DocumentTable.number eq id.number)
            })
            {
                println(toDateTime(date))
                it[this.date] = toDateTime(date)
                it[this.description] = description
            } == 1)
        }
    }

    @Throws(AccException::class)
    override fun deleteDoc(doc: Document) {
        transaction {
            DocumentTable.deleteWhere {
                DocumentTable.id eq doc.id// typeName eq id.type.name) and (DocumentTable.number eq id.number)
            }
        }

    }

    override fun findFreeDocNumber(docType: DocType): Int {
        return allDocs.stream()
                .filter { it.type == docType }
                .mapToInt { it.number }
                .max().orElse(0) + 1
    }

}