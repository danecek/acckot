package acc.integration

import acc.Options
import acc.model.*
import acc.model.Transaction
import acc.util.AccException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.time.LocalDate
import com.google.common.cache.CacheLoader
import java.util.concurrent.TimeUnit
import com.google.common.cache.CacheBuilder


/**
 * Convert [java.time.LocalDate] to [org.joda.time.DateTime]
 */
/*fun toDateTime(localDate: LocalDate): DateTime {
    return DateTime(DateTimeZone.UTC).withDate(
            localDate.year, localDate.monthValue, localDate.dayOfMonth
    ).withTime(1, 1, 1, 1)
}

*//**
 * Convert [org.joda.time.DateTime] to [java.time.LocalDate]
 *//*
fun toLocalDate(dateTime: DateTime): LocalDate {
    val dateTimeUtc = dateTime.withZone(DateTimeZone.UTC)
    return LocalDate.of(dateTimeUtc.year, dateTimeUtc.monthOfYear, dateTimeUtc.dayOfMonth)
}*/

/*
object AccDAOH2 : DocumentDAOInterface by DocumentCache,
        TransDAOInterface by TransactionCache {

    fun dataInit() {
        AccountCache.load()
        DocumentCache.dataInit()
        TransactionCache.dataInit()
        Database.connect(url = "jdbc:h2:/" + Options.h2File,
                driver = "org.h2.Driver")
        transaction {
            //      addLogger(StdOutSqlLogger)
            SchemaUtils.create(TransactionTable, DocumentTable)
            DocumentTable.selectAll()
                    .forEach {
                        DocumentCache.createDoc(
                                DocType.valueOf(it[DocumentTable.typeName]),
                                it[DocumentTable.number],
                                toLocalDate(it[DocumentTable.date]),
                                it[DocumentTable.description])
                    }


            TransactionTable.selectAll()
                    .forEach {
                        TransactionCache.createTrans(
                                TransactionId(it[TransactionTable.id]),
                                it[TransactionTable.amount],
                                AccountCache.accById(
                                        AnalId(
                                                Osnova.groupByNumber(it[TransactionTable.maDatiSyntAcc]),
                                                it[TransactionTable.maDatiAnal]
                                        )),
                                AccountCache.accById(
                                        AnalId(
                                                Osnova.groupByNumber(it[TransactionTable.dalSyntAcc]),
                                                it[TransactionTable.dalAnal]
                                        )),
                                findDoc(it),
                                findRelatedDoc(it)
                        )
                    }

        }
    }

    private fun findDoc(it: ResultRow): Document {
        return DocumentCache.docById(
                DocId(DocType.valueOf(it[TransactionTable.documentType]),
                        it[TransactionTable.documentNumber]))
    }

    private fun findRelatedDoc(it: ResultRow): Document? {
        val docTypeName = it[TransactionTable.relatedDocumentType]
        return if (docTypeName.isNullOrBlank()) null
        else DocumentCache.docById(
                DocId(DocType.valueOf(docTypeName), it[TransactionTable.relatedDocumentNumber]))
    }

    // Transakce ***************************************************************************
    @Throws(AccException::class)
    override fun createTrans(id: TransactionId?, amount: Long, maDati: AnalAcc,
                                   dal: AnalAcc, document: Document, relatedDocument: Document?) {
        assert(id == null)
        transaction {
            val genId = TransactionTable.insert {
                it[this.amount] = amount
                it[this.maDatiAnal] = maDati.anal
                it[this.maDatiSyntAcc] = maDati.syntAccount.number
                it[this.dalAnal] = dal.anal
                it[this.dalSyntAcc] = dal.syntAccount.number
                it[this.documentType] = document.id.type.name
                it[this.documentNumber] = document.id.number
                it[this.relatedDocumentType] = relatedDocument?.id?.type?.name ?: ""
                it[this.relatedDocumentNumber] = relatedDocument?.id?.number ?: 0
            }.generatedKey
            TransactionCache.createTrans(TransactionId(genId!!.toInt()),
                    amount, maDati, dal, document, relatedDocument)
        }

    }

    @Throws(AccException::class)
    override fun updateTrans(id: TransactionId, amount: Long,
                             maDati: AnalAcc, dal: AnalAcc, document: Document,
                             relatedDocument: Document?) {
        transaction {
            val n = TransactionTable.update({
                (TransactionTable.id eq id.id)
            }) {
                it[this.amount] = amount
                it[this.maDatiAnal] = maDati.anal
                it[this.maDatiSyntAcc] = maDati.syntAccount.number
                it[this.dalAnal] = dal.anal
                it[this.dalSyntAcc] = dal.syntAccount.number
                it[this.documentType] = document.type.name
                it[this.documentNumber] = document.number
                it[this.relatedDocumentType] = relatedDocument?.type?.name ?: ""
                it[this.relatedDocumentNumber] = relatedDocument?.number ?: 0
            }
            assert(n == 1)
            TransactionCache.updateTrans(id, amount, maDati, dal, document,
                    relatedDocument)
        }
    }

    @Throws(AccException::class)
    override fun deleteTrans(id: TransactionId) {
        transaction {
            TransactionCache.deleteTrans(id)
            TransactionTable.deleteWhere {
                (TransactionTable.id eq id.id)
            }
            TransactionCache.deleteTrans(id)
        }

    }


    // Doklady ***************************************************************************
    override val allDocs: List<Document>
        get() = DocumentCache.allDocs

    override fun createDoc(type: DocType, number: Int, date: LocalDate,
                           description: String) {
        DocumentCache.createDoc(type, number, date, description)
        transaction {
            DocumentTable.insert {
                it[this.typeName] = type.name
                it[this.number] = number
                it[this.date] = toDateTime(date)
                it[this.description] = description
            }
        }
    }

    override fun updateDoc(id: DocId, date: LocalDate,
                           description: String) {
        transaction {
            DocumentTable.update({
                (DocumentTable.typeName eq id.type.name) and (DocumentTable.number eq id.number)
            })
            {
                it[this.date] = toDateTime(date)
                it[this.description] = description
            }
            DocumentCache.updateDoc(id, date, description)
        }
    }

    @Throws(AccException::class)
    override fun deleteDoc(id: DocId) {
        transaction {
            DocumentTable.deleteWhere {
                (DocumentTable.typeName eq id.type.name) and (DocumentTable.number eq id.number)
            }
            DocumentCache.deleteDoc(id)
        }

    }

}*/
