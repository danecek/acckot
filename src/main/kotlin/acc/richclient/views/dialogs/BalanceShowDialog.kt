package acc.richclient.views.dialogs

import acc.richclient.views.panes.BalanceView
import acc.richclient.views.PaneTabs
import acc.util.Global
import acc.util.Messages
import acc.util.monthFormater
import acc.util.withColon
import javafx.beans.property.SimpleObjectProperty
import javafx.util.StringConverter
import tornadofx.*
import java.lang.UnsupportedOperationException
import java.time.Month
import java.time.format.DateTimeFormatter


class BalanceShowDialog : Fragment() {


    val month = SimpleObjectProperty<Month>()

    override val root =
            form {
                title = Messages.Rozvaha.cm()
                fieldset {
                    field(Messages.Mesice.cm().withColon) {
                        combobox(month, Month.values().toList().observable()) {
                            value = Month.DECEMBER
                            converter = object : StringConverter<Month>() {

                                override fun toString(obj: Month): String {
                                    return monthFormater.format(obj)
                                }

                                override fun fromString(string: String): Month {
                                    throw UnsupportedOperationException()
                                }
                            }

                        }
                    }

                }
                buttonbar {
                    button(Messages.Potvrd.cm()) {
                        action {
                            PaneTabs.addTab(Messages.Rozvaha.cm(),
                                    find<BalanceView>(mapOf("month" to month)).root)
                            close()
                        }

                    }
                    button(Messages.Zrus.cm()) {
                        action {
                            close()
                        }
                    }
                }
            }
}