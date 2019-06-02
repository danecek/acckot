package acc.richclient.dialogs

import acc.richclient.panes.PaneTabs
import acc.richclient.panes.SheetPaneFragment
import acc.util.Messages
import acc.util.monthFrm
import acc.util.withColon
import javafx.beans.property.SimpleObjectProperty
import javafx.util.StringConverter
import tornadofx.*
import java.time.Month


class BalanceShowDialog : Fragment() {

    private val sheet: Messages by params
    private val month = SimpleObjectProperty<Month>()
    private val isc = checkbox(Messages.Zobraz_synteticke_ucty.cm())

    override val root =
            form {
                spacing = 20.0
                title = when (sheet) {
                    Messages.Zisky_a_ztraty -> Messages.Vytvor_zisky_a_ztraty.cm()
                    Messages.Rozvaha -> Messages.Vytvor_rozvahu.cm()
                    else -> kotlin.error(sheet.toString())
                }
                fieldset {

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
                    field() {
                        add(isc)
                    }
                }
                buttonbar {
                    button(Messages.Potvrd.cm()) {
                        action {
                            PaneTabs.addTab(sheet.cm(),
                                    find<SheetPaneFragment>(mapOf("month" to month,
                                            "sheet" to sheet,
                                            "includeSyntAccount" to isc.isSelected)).root)
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