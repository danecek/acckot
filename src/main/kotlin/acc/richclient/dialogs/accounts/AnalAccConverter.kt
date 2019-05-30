package acc.richclient.dialogs.accounts

import acc.model.AccGroup
import acc.model.AnalAcc
import javafx.util.StringConverter

object AnalAccConverter : StringConverter<AnalAcc>() {
    override fun toString(acc: AnalAcc?): String {
        return acc?.numberName() ?: ""
    }

    override fun fromString(string: String?): AnalAcc {
        throw UnsupportedOperationException()
    }

}

object AccGroupConverter : StringConverter<AccGroup>() {
    override fun toString(acc: AccGroup?): String {
        return acc?.numberName() ?: ""
    }

    override fun fromString(string: String?): AccGroup {
        throw UnsupportedOperationException()
    }

}