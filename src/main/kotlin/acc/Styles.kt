package acc

import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val welcomeScreen by cssclass()
        val content by cssclass()
        val heading by cssclass()
        val tabPane by cssclass()
        val control by cssclass()
        val textField by cssclass()
        val comboBox by cssclass()
    }

    init {
        titledPane {
            prefWidth = 1000.px
        }
        datePicker {
            fontSize = 30.px
        }
        textField {
            fontSize = 30.px
        }
        comboBox {
            fontSize = 30.px
        }
        tabPane {
            fontSize = 30.px
        }
        label {
            fontSize = 30.px
        }
        menuBar {
            fontSize = 30.px
        }
        button {
            fontSize = 30.px
        }
        content {
            fontSize = 30.px
        }
        welcomeScreen {
            padding = box(10.px)
            backgroundColor += LinearGradient(0.0, 0.0, 0.0, 1.0, true,
                    CycleMethod.NO_CYCLE, Stop(0.0, c("#028aff")),
                    Stop(1.0, c("#003780")))
            heading {
                fontSize = 3.em
                textFill = Color.WHITE
                fontWeight = FontWeight.BOLD
            }
            content {
                padding = box(25.px)
                button {
                    fontSize = 30.px
                }
            }
        }
    }
}