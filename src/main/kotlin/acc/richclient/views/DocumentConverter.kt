package acc.richclient.views

import acc.model.Document
import acc.util.withColon
import javafx.util.StringConverter

object DocumentConverter : StringConverter<Document>() {
    override fun fromString(string: String?): Document =
            throw UnsupportedOperationException()

    override fun toString(doc: Document?): String =
            if (doc == null) ""
            else doc.type.text+doc.number.toString().withColon + doc.description.take(50)

}