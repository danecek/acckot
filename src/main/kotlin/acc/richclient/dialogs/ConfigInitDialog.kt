package acc.richclient.dialogs

import acc.Options
import acc.richclient.MainView
import acc.util.Messages
import acc.util.withColon
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate
import java.time.Year

class ConfigInitDialog : View() {

    private val rok = SimpleStringProperty(this, "rok", config.string("rok"))
    private val accFont = SimpleStringProperty(this, "rok", config.string("font"))
    private val vm = ViewModel()

    init {
        title = Messages.Nastaveni.cm()
        if (rok.value.isNullOrBlank())
            rok.value = LocalDate.now().year.toString()
        if (accFont.value.isNullOrBlank())
            accFont.value = "15"
    }

    override val root = form {
        fieldset {

            field(Messages.Rok.cm().withColon) {
                val tf = textfield(rok)
                vm.addValidator(tf, rok) {
                    try {
                        Options.year = Year.parse(rok.value).value
                        null
                    } catch (ex: Exception) {
                        error(Messages.Neplatny_rok.cm())
                    }
                }
            }
            field(Messages.Velikost_pisma.cm()) {
                vm.addValidator(textfield(accFont), accFont) {
                    try {
                        Options.fontSize = accFont.value.toInt()
                        null
                    } catch (ex: Exception) {
                        error(Messages.Neplatna_velikost.cm())
                    }
                }
            }

            buttonbar {
                button(Messages.Potvrd.cm()) {
                    enableWhen(vm.valid)
                    action {
                        with(config) {
                            set("rok" to rok.value)
                            set("font" to accFont.value)

                            save()
                        }
                        if (!Options.dataFolder.exists())
                            Options.dataFolder.mkdir()
                      //  AccDAOH2.dataInit()
                        //    primaryStage.isResizable = true
                        primaryStage.isMaximized = true
                        primaryStage.centerOnScreen()
                        replaceWith<MainView>()
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

}


