package acc.richclient.dialogs

import acc.richclient.panes.BalancePaneView
import acc.richclient.PaneTabs
import acc.util.monthFrm
import acc.Options
import acc.util.Messages
import acc.util.withColon
import javafx.beans.property.SimpleObjectProperty
import javafx.util.StringConverter
import tornadofx.*
import java.lang.UnsupportedOperationException
import java.time.Month


class BalanceShowDialog : Fragment() {


    val month = SimpleObjectProperty<Month>()

    override val root =
            form {
                title = Messages.Rozvaha.cm()
                fieldset {
                    spacing = Options.fieldsetSpacing
                    prefWidth = Options.fieldsetPrefWidth
                    field(Messages.Mesice.cm().withColon) {
                        combobox(month, Month.values().toList()) {
                            value = Month.DECEMBER
                            converter = object : StringConverter<Month>() {

                                override fun toString(obj: Month): String {
                                    return monthFrm.format(obj)
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
                                    find<BalancePaneView>(mapOf("month" to month)).root)
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