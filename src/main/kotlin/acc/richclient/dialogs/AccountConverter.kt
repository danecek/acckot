package acc.richclient.dialogs

import acc.model.AnalAcc
import javafx.util.StringConverter

object AccountConverter : StringConverter<AnalAcc>() {
    override fun toString(acc: AnalAcc?): String {
        return acc?.numberName ?: ""
    }

    override fun fromString(string: String?): AnalAcc {
        throw UnsupportedOperationException()
    }

}