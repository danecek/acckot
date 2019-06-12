package acc.richclient.dialogs

import acc.richclient.MainView
import acc.util.Messages
import acc.util.withColon
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeParseException

class ConfigInitDialog : View(Messages.Nastaveni.cm()) {

    companion object {
        val fontSize
            get() = find<ConfigInitDialog>().fontSize
        val dataFolder
            get() = find<ConfigInitDialog>().dataFolder
        val year
            get() = find<ConfigInitDialog>().year
        val h2File
            get() = Paths.get(dataFolder, "ucetnidata$year")
        val accountFile
            get() = Paths.get(dataFolder, "ucty$year.json")
    }

    val fontSize
        get() = app.config.string(key = "fontSize", defaultValue = "15").toInt()

    val year
        get() = app.config.string(key = "year", defaultValue = LocalDate.now().year.toString()).toInt()

    val dataFolder
        get() = app.config.string("dataFolder",
                defaultValue = System.getProperty("user.home").plus("/UcetnictviData"))

    val yearProp = SimpleStringProperty(this, null, year.toString())
    val fontSizeProp =
            SimpleStringProperty(this, null,
                    app.config.string(key = "fontSize", defaultValue = "15"))
    val dataFolderProp =
            SimpleStringProperty(this, null,
                    app.config.string("dataFolder", dataFolder))
    private val vm = ViewModel()

    override val root = form {
        // prefWidth = 1000.0
        fieldset {
            padding = insets(10.0)
            field(Messages.Adresar_s_daty.cm().withColon) {
                hbox {
                    spacing = 10.0
                    vm.addValidator(textfield(dataFolderProp) {
                        prefColumnCount = 30
                    }, dataFolderProp){
                        if (File(dataFolderProp.value).listFiles()?.all {
                                    it.nameWithoutExtension.startsWith("ucty") ||
                                            it.nameWithoutExtension.startsWith("ucetnidata")
                                }?:true)
                            null
                        else error(Messages.Neni_ucetni_adresar.cm())
                    }
                    button(Messages.Zvol_ucetni_adresar.cm()) {
                        action {
                            chooseDirectory()?.run {
                                    dataFolderProp.value = toString()
                            }
                        }
                    }

                }

            }
            field(Messages.Rok.cm().withColon) {
                vm.addValidator(textfield(yearProp), yearProp) {
                    try {
                        val y = Year.parse(yearProp.value)!!
                        if (y.isBefore(Year.of(1900)) || y.isAfter(Year.of(2100)))
                            this.error(Messages.Neplatny_rok.cm())
                        else
                            null
                    } catch (ex: DateTimeParseException) {
                        error(Messages.Neplatny_rok.cm())
                    }
                }
            }
            field(Messages.Velikost_pisma.cm().withColon) {
                vm.addValidator(textfield(fontSizeProp), fontSizeProp) {
                    try {
                        val fs = fontSizeProp.value.toInt()
                        if (fs < 5 || fs > 50)
                            error(Messages.Neplatna_velikost.cm())
                        else
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
                        with(app.config) {
                            set("year" to yearProp.value)
                            set("fontSize" to fontSizeProp.value)
                            set("dataFolder" to dataFolderProp.value)
                            save()
                        }
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
