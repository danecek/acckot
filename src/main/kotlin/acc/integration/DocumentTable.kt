package acc.integration

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.joda.time.DateTime

object DocumentTable : IntIdTable() {
    val typeName = varchar("typeName", 20)
    val number = integer("number")
    val date:Column<DateTime> = date("date")
    val description = varchar("desc", 200)
}