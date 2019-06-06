package acc

import tornadofx.*
import java.io.File
import java.util.*

object Options : Component() {
    val locale = Locale("cs")

   // var year = 2019
    val fontSize=20
   //     get() = app.config.string(key = "year").toInt()
    var rootPrefWidth = 1600
    var rootPrefHeight = 1000

    val dataFolder
        get() = File(System.getProperty("user.home") + "/.ucetnictvi")
//    val h2File
//        get() = File(dataFolder, "ucetnidata$year")
    val accountFile
        get() = File(dataFolder, "ucty2000.json")
    const val nameCrop: Int = 30
    val prefTableHeight: Double = Double.MAX_VALUE

}

