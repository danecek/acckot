package acc.integration

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object DocumentTable : Table() {
    val typeName = varchar("typeName", 20)
    val number = integer("number")
    val date: Column<DateTime> = date("date")
    val description = varchar("desc", 200)
}