package acc.richclient.dialogs

import acc.integration.AccDAOH2
import acc.richclient.MainView
import acc.Options
import acc.util.Messages
import acc.util.withColon
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate
import java.time.Year

class ConfigInitDialog : View() {

    val rok = SimpleStringProperty(this, "rok", config.string("rok"))
    val vm = ViewModel()

    init {
        title = Messages.Nastaveni.cm()
        if (rok.value.isNullOrBlank())
            rok.value = LocalDate.now().year.toString()
    }

    override val root = form {
        fieldset {
            spacing = Options.fieldsetSpacing
            prefWidth = Options.fieldsetPrefWidth
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

            buttonbar {
                button(Messages.Potvrd.cm()) {
                    enableWhen(vm.valid)
                    action {
                        with(config) {
                            set("rok" to rok.value)
                            save()
                        }
                        if (!Options.dataFolder.exists())
                            Options.dataFolder.mkdir()
                        AccDAOH2.dataInit()
                        primaryStage.isResizable = true
                        primaryStage.height = Options.primaryStageHeight
                        primaryStage.width = Options.primaryStageWidth
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


