package acc

import tornadofx.*


class Styles : Stylesheet() {
    companion object {
        val root by cssclass()
/*        val welcomeScreen by cssclass()
        val content by cssclass()
        val heading by cssclass()
        val tabPane by cssclass()
        val control by cssclass()
        val textField by cssclass()
        val comboBox by cssclass()*/
    }

    init {

 root {
     fontSize = Options.fontSize.px
//     prefWidth = Options.rootPrefWidth.px
    // prefHeight = Options.rootPrefHeight.px

 }
        /*
form {
fontSize = accFontSize.px
}
tableView {
fontSize = accFontSize.px
}
dialogPane {
fontSize = accFontSize.px
}

header {
fontSize = accFontSize.px

}

default {
fontSize = accFontSize.px
}
textInput {

}
tableView {
prefWidth = 2000.px
}
radioButton {
fontSize = 30.px
}
textArea {
fontSize = 30.px
}
checkBox {
fontSize = 30.px
}
titledPane {
prefWidth = 1000.px
}
datePicker {
fontSize = 30.px
}
textField {
fontSize = 30.px
}
comboBox {
fontSize = 30.px
}
tabPane {
fontSize = 30.px
}
label {
fontSize = 30.px
}
menuBar {
fontSize = 30.px
}
button {
fontSize = 30.px
}
content {
fontSize = 30.px
}
welcomeScreen {
padding = box(10.px)
backgroundColor += LinearGradient(0.0, 0.0, 0.0, 1.0, true,
     CycleMethod.NO_CYCLE, Stop(0.0, c("#028aff")),
     Stop(1.0, c("#003780")))
heading {
 fontSize = 3.em
 textFill = Color.WHITE
 fontWeight = FontWeight.BOLD
}
content {
 padding = box(25.px)
 button {
     fontSize = 30.px
 }
}
}*/
    }
}