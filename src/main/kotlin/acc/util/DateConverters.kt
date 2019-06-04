package acc.util

import acc.Options
import javafx.util.StringConverter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val dayMonthFrm: DateTimeFormatter = DateTimeFormatter//.ofLocalizedDate(FormatStyle.MEDIUM)
        .ofPattern("d.M.")
        .withLocale(Options.locale)

val monthFrm: DateTimeFormatter = DateTimeFormatter
        .ofPattern("MMMM")
        .withLocale(Options.locale)

fun LocalDate.dateFormat(): String = dayMonthFrm.format(this)

fun initDate(): LocalDate {
    val now = LocalDate.now()
    return if (now.year == Options.year) now
    else LocalDate.of(Options.year, 1, 1)
}

object DayMonthConverter : StringConverter<LocalDate>() {
    override fun toString(date: LocalDate?): String {
        return if (date != null) {
            dayMonthFrm.format(date)
        } else {
            ""
        }
    }

    override fun fromString(string: String?): LocalDate? {
        return if (string != null && !string.isEmpty()) {
            LocalDate.parse(string, dayMonthFrm)
        } else {
            null
        }
    }
}

/**
 * Convert [java.time.LocalDate] to [org.joda.time.DateTime]
 */
fun toDateTime(localDate: LocalDate): DateTime {

    val dt = DateTime(DateTimeZone.UTC).withDate(
            localDate.year, localDate.monthValue, localDate.dayOfMonth
    ).withTime(1, 1, 1, 1)
  //  println("toDateTime: $localDate->$dt")
    return dt;
}

/**
 * Convert [org.joda.time.DateTime] to [java.time.LocalDate]
 */
fun toLocalDate(dateTime: DateTime): LocalDate {
    val dateTimeUtc = dateTime.withZone(DateTimeZone.UTC)

    val ld = LocalDate.of(dateTimeUtc.year, dateTimeUtc.monthOfYear, dateTimeUtc.dayOfMonth+1)
  //  println("toLocalDate: $dateTime->$ld")
    return ld
}