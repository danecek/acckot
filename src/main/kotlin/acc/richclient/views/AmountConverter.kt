package acc.richclient.views

import javafx.util.StringConverter

object AmountConverter : StringConverter<Long>() {
    override fun toString(obj: Long?): String {
        return obj.toString()
    }

    override fun fromString(string: String?): Long {
        try {
            return string?.toLong() ?: 0
        } catch (ex: Exception) {
            return 0;
        }

    }

}