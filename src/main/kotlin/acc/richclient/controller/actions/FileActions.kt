package acc.richclient.controller.actions

import javafx.application.Platform

object Exit : AbstrAction() {
    override val name = "Exit"
    override fun execute() {
        Platform.exit()
    }
}



