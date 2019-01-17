package acc.richclient.controller.actions

import javafx.event.ActionEvent
import javafx.scene.Parent
import javafx.stage.StageStyle
import tornadofx.*

abstract class AbstrAction : (ActionEvent) -> Unit {
    override fun invoke(p1: ActionEvent) {
        execute()
    }

    abstract val name: String
    abstract fun execute()
}