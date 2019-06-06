package acc.util

import javafx.application.Platform
import javafx.scene.control.Alert
import org.apache.logging.log4j.LogManager
import tornadofx.*

private val LOGGER = LogManager.getLogger()

class AccException(message: String, cause: Exception? = null) : Exception(message, cause)

fun Messages.fxAlert() {
    fxAlert(cm())
}

fun fxAlert(e: String) {
    Platform.runLater {
        alert(Alert.AlertType.ERROR, e)
    }
}

fun accFail(e: Throwable) {
    fxAlert(e.message ?: e.toString())
    LOGGER.catching(e)
}

fun catchAccException(block: () -> Unit) {
    try {
        block()
    } catch (ex: AccException) {
        accFail(ex)
    }
}
