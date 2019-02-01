package acc.util

import javafx.scene.control.Alert
import tornadofx.*

class AccException(message: String, ex: Exception?=null) : Exception(message, ex)

fun accError(e: String) {
    alert(Alert.AlertType.ERROR, e)
}
fun accError(e: Throwable) {
    accError(e.message?:e.toString())
    e.printStackTrace()
}
