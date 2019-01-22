package acc.richclient.controller

import javafx.application.Platform

object Exit : AbstrAction() {
    override val name = "Exit"
    override fun execute() {
        Platform.exit()
    }
}



