package acc

import java.io.File
import java.util.*

object Options {
    val locale = Locale("cs")

    var year = 2019
    var fontSize = 20
    var rootPrefWidth = 1600
    var rootPrefHeight = 1000

/*    const val fieldsetPrefWidth = 800.0
    const val fieldsetSpacing = 5.0
    const val primaryStageWidth = 2000.0
    const val primaryStageHeight = 1500.0*/


    val dataFolder
        get() = File(System.getProperty("user.dir") + "/ucetnictvi")
    val h2File
        get() = File(dataFolder, "ucetnidata$year")
    val accountFile
        get() = File(dataFolder, "ucty$year.json")
    const val nameCrop: Int = 30
    val prefTableHeight: Double = Double.MAX_VALUE


}

