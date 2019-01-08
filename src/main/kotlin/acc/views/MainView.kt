package acc.views

import acc.Styles
import javafx.scene.control.Alert.AlertType.INFORMATION
import javafx.scene.control.Label
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    override val root = borderpane {
        addClass(Styles.welcomeScreen)
        top {
             stackpane {
                 Label("xxxxxx")
               // label(title).addClass(Styles.heading)

            }
        }
        center {
            tabpane() {
                addClass(Styles.content)
                button("Click me") {
                    setOnAction {
                        alert(INFORMATION, "Well done!", "You clicked me!")
                    }
                }
            }
        }
    }
}