package acc.richclient.views

import acc.util.Global
import javafx.util.StringConverter
import java.time.LocalDate

object DateConverter : StringConverter<LocalDate>() {
    override fun toString(date: LocalDate?): String {
        return if (date != null) {
            Global.df.format(date)
        } else {
            ""
        }
    }

    override fun fromString(string: String?): LocalDate? {
        return if (string != null && !string.isEmpty()) {
            LocalDate.parse(string, Global.df)
        } else {
            null
        }
    }
}