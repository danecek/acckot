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
        return allTrans.filter { tf?.match(it) != false }
                .toMutableList()
    }

    override fun docById(id: DocId): Document {
        return transaction {
            DocumentTable.select { DocumentTable.number eq id.number }
                    .map {
                        Document(
                                DocType.valueOf(it[DocumentTable.typeName]),
                                it[DocumentTable.number],
                                toLocalDate(it[DocumentTable.date]),
                                it[DocumentTable.description])
                    }.first()

        }
    }

    fun trans(): List<Transaction> {
        lateinit var res: List<Transaction>
        transaction {
            val x = TransactionTable.selectAll()

                    .limit(10, offset = 5)


            x.map { r ->
                val docId = DocId(DocType.valueOf(r[TransactionTable.documentType]),
                        r[TransactionTable.documentNumber])
                val doc = docById(docId)//  documentCache[docId]

                val dn = r[TransactionTable.relatedDocumentType]
                var relDoc: Document? = null
                if (!dn.isNullOrBlank()) {
                    val relDocId = DocId(DocType.valueOf(r[TransactionTable.relatedDocumentType]),
                            r[TransactionTable.relatedDocumentNumber])
                    relDoc = docById(relDocId)//  documentCache[relDocId]
                }
                val maDatiSynt = Osnova.groupByNumber(r[TransactionTable.maDatiSyntAcc])
                val maDatiAnalId = AnalId(maDatiSynt, r[TransactionTable.maDatiAnal])
                val maDati = AccountCache.accById(maDatiAnalId)

                val dalSynt = Osnova.groupByNumber(r[TransactionTable.dalSyntAcc])
                val dalAnalId = AnalId(dalSynt, r[TransactionTable.dalAnal])
                val dal = AccountCache.accById(dalAnalId)
                Transaction(
                        TransactionId(r[TransactionTable.id]),
                        r[TransactionTable.amount],
                        maDati,
                        dal,
                        doc,
                        relDoc)
            }.toMutableList()
        }
        return res
    }

    override fun limitTrans(n:Int, offset:Int): List<Transaction> {
            lateinit var res: List<Transaction>
            transaction {
                res = TransactionTable.selectAll()
                        .limit(n, offset)
                        .map { r ->
                            val docId = DocId(DocType.valueOf(r[TransactionTable.documentType]),
                                    r[TransactionTable.documentNumber])
                            val doc = docById(docId)//  documentCache[docId]

                            val dn = r[TransactionTable.relatedDocumentType]
                            var relDoc: Document? = null
                            if (!dn.isNullOrBlank()) {
                                val relDocId = DocId(DocType.valueOf(r[TransactionTable.relatedDocumentType]),
                                        r[TransactionTable.relatedDocumentNumber])
                                relDoc = docById(relDocId)//  documentCache[relDocId]
                            }
                            val maDatiSynt = Osnova.groupByNumber(r[TransactionTable.maDatiSyntAcc])
                            val maDatiAnalId = AnalId(maDatiSynt, r[TransactionTable.maDatiAnal])
                            val maDati = AccountCache.accById(maDatiAnalId)

                            val dalSynt = Osnova.groupByNumber(r[TransactionTable.dalSyntAcc])
                            val dalAnalId = AnalId(dalSynt, r[TransactionTable.dalAnal])
                            val dal = AccountCache.accById(dalAnalId)
                            Transaction(
                                    TransactionId(r[TransactionTable.id]),
                                    r[TransactionTable.amount],
                                    maDati,
                                    dal,
                                    doc,
                                    relDoc)
                        }.toMutableList()
            }
            return res
        }

    override val allTrans: List<Transaction>
        get() {
            lateinit var res: List<Transaction>
            transaction {
                res = TransactionTable.selectAll()
                        .map { r ->
                            val docId = DocId(DocType.valueOf(r[TransactionTable.documentType]),
                                    r[TransactionTable.documentNumber])
                            val doc = docById(docId)//  documentCache[docId]

                            val dn = r[TransactionTable.relatedDocumentType]
                            var relDoc: Document? = null
                            if (!dn.isNullOrBlank()) {
                                val relDocId = DocId(DocType.valueOf(r[TransactionTable.relatedDocumentType]),
                                        r[TransactionTable.relatedDocumentNumber])
                                relDoc = docById(relDocId)//  documentCache[relDocId]
                            }
                            val maDatiSynt = Osnova.groupByNumber(r[TransactionTable.maDatiSyntAcc])
                            val maDatiAnalId = AnalId(maDatiSynt, r[TransactionTable.maDatiAnal])
                            val maDati = AccountCache.accById(maDatiAnalId)

                            val dalSynt = Osnova.groupByNumber(r[TransactionTable.dalSyntAcc])
                            val dalAnalId = AnalId(dalSynt, r[TransactionTable.dalAnal])
                            val dal = AccountCache.accById(dalAnalId)
                            Transaction(
                                    TransactionId(r[TransactionTable.id]),
                                    r[TransactionTable.amount],
                                    maDati,
                                    dal,
                                    doc,
                                    relDoc)
                        }.toMutableList()
            }
            return res
        }

    @Throws(AccException::class)
    override fun createTrans(id: TransactionId?, amount: Long, maDati: AnalAcc,
                             dal: AnalAcc, document: Document, relatedDocument: Document?) {
        assert(id == null)
        transaction {
            TransactionTable.insert {
                it[this.amount] = amount
                it[this.maDatiAnal] = maDati.anal
                it[this.maDatiSyntAcc] = maDati.syntAccount.number
                it[this.dalAnal] = dal.anal
                it[this.dalSyntAcc] = dal.syntAccount.number
                it[this.documentType] = document.id.type.name
                it[this.documentNumber] = document.id.number
                it[this.relatedDocumentType] = relatedDocument?.id?.type?.name ?: ""
                it[this.relatedDocumentNumber] = relatedDocument?.id?.number ?: 0
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
                it[this.maDatiAnal] = maDati.anal
                it[this.maDatiSyntAcc] = maDati.syntAccount.number
                it[this.dalAnal] = dal.anal
                it[this.dalSyntAcc] = dal.syntAccount.number
                it[this.documentType] = document.type.name
                it[this.documentNumber] = document.number
                it[this.relatedDocumentType] = relatedDocument?.type?.name ?: ""
                it[this.relatedDocumentNumber] = relatedDocument?.number ?: 0
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
                                    DocType.valueOf(it[DocumentTable.typeName]),
                                    it[DocumentTable.number],
                                    toLocalDate(it[DocumentTable.date]),
                                    it[DocumentTable.description])
                        }.toMutableList()
            }


    override fun docsByFilter(docFilter: DocFilter?): List<Document> {
        return allDocs.filter { docFilter?.matchDoc(it) != false }.toMutableList()
    }

    override fun createDoc(type: DocType, number: Int, date: LocalDate,
                           description: String) {
        transaction {
            DocumentTable.insert {
                it[this.typeName] = type.name
                it[this.number] = number
                it[this.date] = toDateTime(date)
                it[this.description] = description
            }
        }
    }

    override fun updateDoc(id: DocId, date: LocalDate, description: String) {
        transaction {
            val n = DocumentTable.update({
                (DocumentTable.typeName eq id.type.name) and (DocumentTable.number eq id.number)
            })
            {
                it[this.date] = toDateTime(date)
                it[this.description] = description
            }
            assert(n == 1)
        }
    }

    @Throws(AccException::class)
    override fun deleteDoc(id: DocId) {
        transaction {
            DocumentTable.deleteWhere {
                (DocumentTable.typeName eq id.type.name) and (DocumentTable.number eq id.number)
            }
        }

    }

    override fun findFreeDocId(docType: DocType): Int {
        return allDocs.stream()
                .filter { it.type == docType }
                .mapToInt { it.number }
                .max().orElse(0) + 1
    }

}