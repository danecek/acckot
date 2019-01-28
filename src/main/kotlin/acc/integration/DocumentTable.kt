package acc.integration

import org.jetbrains.exposed.sql.Table

object DocumentTable : Table() {
    val type = varchar("type", 20)
    val number = integer("number")
    val date = date("date")
    val description = varchar("desc", 200)
}