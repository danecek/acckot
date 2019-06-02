package acc.richclient.dialogs.trans

import acc.model.AnalAcc

data class AccWrapper(var acc: AnalAcc?=null) {
    // workaround
    override fun toString() = acc.toString()
}