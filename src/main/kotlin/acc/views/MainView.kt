package acc.views

import acc.Styles
import acc.richclient.controller.AccMenuBar
import com.sun.xml.internal.ws.server.provider.ProviderInvokerTube
import javafx.scene.control.Alert.AlertType.INFORMATION
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import tornadofx.*


fun x(i: MenuItem) {

}
//val x = {mi : MenuItem -> println() }


class MainView : View("Hello TornadoFX") {
    override val root = borderpane {
        addClass(Styles.welcomeScreen)
        top {
            menubar {
                menu("File") {

                    item("xxxxxxx","xxxx" ,null, ::x)
                    item("xxxxxxx") .action {

                    }
                    item("xxxxxxx") {
                        println("XXXXXXXXX")
                    }
                }
                menu("Tornado") {
                    item("yyyyy") {
                        println("xccccccccccccccccccccc")
                    }
                    item("yyyyy") {
                        println("xccccccccccccccccccccc")
                    }
                }
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