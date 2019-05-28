package acc.util

import javafx.application.Platform
import javafx.scene.control.Alert
import tornadofx.*

class AccException(message: String, ex: Exception?=null) : Exception(message, ex)

fun accError(e: String) {
    Platform.runLater {
        alert(Alert.AlertType.ERROR, e)
    }
}
fun accError(e: Throwable) {
    accError(e.message?:e.toString())
    e.printStackTrace()
}

fun catchAccException(block:()->Unit) {
    try {
        block()
    } catch (ex: AccException) {
        accError(ex)
    }
}
