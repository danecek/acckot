package acc.richclient.panes

import javafx.scene.Node
import javafx.scene.Parent
import tornadofx.*

abstract class MyDialog(title:String) : Fragment() {

    abstract val cont : Node
    override val root =
            titledpane(title, cont)
/*
            {
                content = cont
            }


            {
                borderpane() {
                    center {
                          fieldset {
                              field("name") {
                                  textfield()
                              }
                          }
                    }
                    bottom {
                        buttonbar {
                            button("Cancel") {
                                action { close() }
                            }
                            button("OK") {
                                action {
                                    println("bla")
                                    close()
                                }
                            }
                        }
                    }

                }

            }*/
}