package acc

import java.io.File
import java.util.*

object Options {
    val locale = Locale("cs")

    var year = 2019
    val fieldsetPrefWidth = 800.0
    val fieldsetSpacing = 5.0
    val primaryStageWidth = 2000.0
    val primaryStageHeight = 1500.0


    val dataFolder
        get() = File(System.getProperty("user.dir") + "/ucetnictvi")
    val h2File
        get() = File(dataFolder, "ucetnidata${year}")
    val accountFile
        get() = File(dataFolder, "ucty${year}.json")
    val nameCrop: Int = 30
    val prefTableHeight: Double = Double.MAX_VALUE


}

