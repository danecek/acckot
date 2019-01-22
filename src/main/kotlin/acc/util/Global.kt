package acc.util

import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object Global {
    val locale = Locale("cs")

    var year = 2019

    val df = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(Global.locale)


}
