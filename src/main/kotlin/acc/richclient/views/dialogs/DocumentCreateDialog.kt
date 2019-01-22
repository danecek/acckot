package acc.richclient.views.dialogs

import acc.business.Facade
import acc.model.DocumentId

class DocumentCreateDialog : DocumentAbstractDialog(DialogMode.CREATE) {

    override val ok: () -> Unit = {
        Facade.createDocument(docModel.type.value,
                docModel.number.value,
                docModel.date.value,
                docModel.description.value ?: ""
        )
        val docId = DocumentId(docModel.type.value, docModel.number.value)
        val doc = Facade.getDocumentById(docId)
        if (doc != null)
            find<TransactionCreateDialog>(params = mapOf("doc" to doc)).openModal()

    }
}