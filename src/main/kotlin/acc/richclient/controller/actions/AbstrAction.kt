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

class X() : Fragment() {

    override val root = borderpane() {
        dialog {  }
    }
}

object TestFragment : AbstrAction() {
    override fun execute() {
        val find = find<X>().openModal(StageStyle.DECORATED)
    }

    override val name: String
        get() = "TestFragment"


}