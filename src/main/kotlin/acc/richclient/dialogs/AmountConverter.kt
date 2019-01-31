package acc.richclient.dialogs

import javafx.util.StringConverter

object AmountConverter : StringConverter<Long>() {
    override fun toString(obj: Long?): String {
        return obj.toString()
    }

    override fun fromString(string: String?): Long {
        return try {
            string?.toLong() ?: 0
        } catch (ex: Exception) {
            0
        }

    }

}