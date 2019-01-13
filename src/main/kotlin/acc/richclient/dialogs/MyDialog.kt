package acc.richclient.dialogs

import acc.util.Messages
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import tornadofx.Fragment

abstract class MyDialog(title: String) : Fragment(title) {

    override val root: Pane
    val content = createCont()
    val gp = GridPane()

    abstract fun createCont(): Pane
    abstract fun ok()
    val cancel = Button(Messages.Zrus.cm())
    val ok = Button(Messages.Potvrd.cm())

    init {
        val bp = BorderPane()
        bp.padding = Insets(10.0)
        bp.center = content
        root = bp
        cancel.setOnAction {
            close()
        }
        ok.setOnAction {
            ok()
            close()
        }
        val buttons = HBox(cancel, ok)
        buttons.spacing = 10.0
        buttons.padding = Insets(10.0)
        buttons.alignment = Pos.CENTER_RIGHT
        bp.bottom = buttons
        gp.padding = Insets(10.0)
        gp.hgap = 10.0
        gp.vgap = 10.0
    }

}


