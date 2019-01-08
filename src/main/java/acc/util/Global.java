package acc.util;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Global {

    public static final Global instance = new Global();
    private Locale locale = new Locale("cs");
    private int year = 2019;

    public DateTimeFormatter df() {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).
                withLocale(Global.instance.getLocale());
    }

    private Global() {
    }

    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

}
