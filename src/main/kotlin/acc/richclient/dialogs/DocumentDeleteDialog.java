package acc.richclient.dialogs;

import acc.business.Facade;
import acc.model.Document;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;

public class DocumentDeleteDialog extends DocumentAbstractDialog {

    private Document d;

    private void disableFields() {
        docTypeCB.setDisable(true);
        nameTF.setDisable(true);
        date.setDisable(true);
        dscTF.setDisable(true);
    }

    public DocumentDeleteDialog(Document d) {
        super(Messages.Zrus_doklad.cm());
        this.d = d;
        disableFields();
        setFields(d);

    }

    @Override
    public void ok() throws AccException {
        Facade.instance.deleteDocument(d.getId());
        MainWindow.instance.refreshDocumentPanes();
    }

    @Override
    public void validate() {

    }

}
